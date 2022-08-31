package com.example.ozeassessment.data.database.dao

import androidx.room.*
import com.example.ozeassessment.data.database.entity.FavoriteUserEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FavoriteUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteUser(favoriteUserEntity: FavoriteUserEntity): Completable

    @Query("SELECT * FROM favorite_user_table ORDER BY id ASC")
    fun readFavoriteUsers(): Flowable<List<FavoriteUserEntity>>

    @Delete
    fun deleteFavoriteUser(favoritesUserEntity: FavoriteUserEntity): Completable

    @Query("DELETE FROM favorite_user_table")
    fun deleteAllFavoriteUsers(): Single<Int>
}