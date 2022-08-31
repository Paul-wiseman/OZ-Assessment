package com.example.ozeassessment.data.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.ozeassessment.data.network.apiservice.GitHubUserApi
import com.example.ozeassessment.models.Item
import com.example.ozeassessment.models.Users
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GithubPagingSource(
    private val service: GitHubUserApi,
) : RxPagingSource<Int, Item>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Item>> {
        val position = params.key ?: 1
        return service.getUsers(page = position)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: Users, position: Int): LoadResult<Int, Item> {
        return LoadResult.Page(
            data = data.users,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (data.users.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}