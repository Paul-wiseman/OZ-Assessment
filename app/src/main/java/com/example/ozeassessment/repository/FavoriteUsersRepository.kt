package com.example.ozeassessment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ozeassessment.data.database.dao.FavoriteUsersDao
import com.example.ozeassessment.data.database.entity.FavoriteUserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteUsersRepository @Inject constructor(
    private val favoriteUsersDao: FavoriteUsersDao,
    val disposable: CompositeDisposable
) {

    private val TAG = this.javaClass.simpleName
    private var _readFavoriteUsersEntity = MutableLiveData<List<FavoriteUserEntity>>()
    val readFavoriteUsersEntity: LiveData<List<FavoriteUserEntity>> get() = _readFavoriteUsersEntity

    fun readFavoritesFromDb() {
        Log.d("updateFavorite", "updateFavorite is called ")
        disposable.add(
            favoriteUsersDao.readFavoriteUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        _readFavoriteUsersEntity.value = it
                    } else {
                        _readFavoriteUsersEntity.value = listOf()
                    }
                    for (i in it) {
                        Log.d(
                            "updateFavorite",
                            "Success in fetching favorite users ----${i.item.id}"
                        )
                    }

                }, {
                    Log.d("updateFavorite", "Exception in fetching favorite users $it")
                })
        )
    }

    fun insertFavoriteUser(favoriteUserEntity: FavoriteUserEntity) =
        disposable.add(
            favoriteUsersDao.insertFavoriteUser(favoriteUserEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "Success insertFavoriteUser:")
                    readFavoritesFromDb()
                }, {
                    Log.d(TAG, "Exception insertFavoriteUser: ------ $it")
                })
        )

    fun deleteFavoriteUser(favoriteUserEntity: FavoriteUserEntity) =
        disposable.add(
            favoriteUsersDao.deleteFavoriteUser(favoriteUserEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    readFavoritesFromDb()
                    Log.d(TAG, "Success deletingFavoriteUser: ---$")
                }, {
                    Log.d(TAG, "Exception deletingFavoriteUser: ---$it")
                })
        )


    fun deleteAllFavoriteUsers() =
        disposable.add(
            favoriteUsersDao.deleteAllFavoriteUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "Exception deleting All FavoriteUser: ---$it")
                    readFavoritesFromDb()
                }, {
                    Log.d(TAG, "Exception deleting All FavoriteUser: ---$it")
                })
        )
}