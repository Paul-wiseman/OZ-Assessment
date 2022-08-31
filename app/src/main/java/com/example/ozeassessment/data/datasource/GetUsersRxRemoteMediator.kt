package com.example.ozeassessment.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.example.ozeassessment.data.database.database.GitHubUsersDatabase
import com.example.ozeassessment.data.database.entity.User
import com.example.ozeassessment.data.database.entity.UsersRemoteKey
import com.example.ozeassessment.data.database.entity.mapper.mapToUser
import com.example.ozeassessment.data.network.apiservice.GitHubUserApi
import com.example.ozeassessment.models.Users
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GetUsersRemoteMediator @Inject constructor(
    private val service: GitHubUserApi,
    private val db:GitHubUsersDatabase
) : RxRemoteMediator<Int, User>() {


    @ExperimentalPagingApi
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, User>
    ): Single<MediatorResult> {
       return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when(it){
                    LoadType.REFRESH -> {

                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?.minus(1)?:1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {

                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?:throw InvalidObjectException("Result is empty")

                        remoteKeys.userId?: INVALID_PAGE
                    }
                }
            }
           .flatMap { page ->
               if (page == INVALID_PAGE){
                   Single.just(MediatorResult.Success(endOfPaginationReached = true))
               }else{
                   service.getUsers(page = page)
                       .map { insertToDb(page, loadType,it) }
                       .map<MediatorResult> {
                           MediatorResult.Success(endOfPaginationReached = it.users.isEmpty())
                       }
               }

           }
           .onErrorReturn { MediatorResult.Error(it) }
    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: Users): Users {
        db.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                db.userRemoteKeyDao().clearRemoteKeys()
                db.usersDao().deleteAll()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.users.isEmpty()) null else page + 1
            val keys = data.users.map {
                UsersRemoteKey(userId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            db.userRemoteKeyDao().insertAll(keys)
            db.usersDao().insertAll(data.users.map { mapToUser(it) })
            db.setTransactionSuccessful()

        } finally {
            db.endTransaction()
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, User>): UsersRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            db.userRemoteKeyDao().remoteKeysByMovieId(repo.userId)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, User>): UsersRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { user ->
            db.userRemoteKeyDao().remoteKeysByMovieId(user.userId)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, User>): UsersRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.userId?.let { id ->
                db.userRemoteKeyDao().remoteKeysByMovieId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}