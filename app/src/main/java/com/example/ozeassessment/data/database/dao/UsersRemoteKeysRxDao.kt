package com.example.ozeassessment.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ozeassessment.data.database.entity.UsersRemoteKey

@Dao
interface UsersRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<UsersRemoteKey>)

    @Query("SELECT * FROM users_remote_keys WHERE userId = :userId")
    fun remoteKeysByMovieId(userId: Int): UsersRemoteKey?

    @Query("DELETE FROM users_remote_keys")
    fun clearRemoteKeys()

}