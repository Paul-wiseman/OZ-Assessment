package com.example.ozeassessment.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users_remote_keys")
data class UsersRemoteKey(
    @PrimaryKey val userId: Int,
    val prevKey: Int?,
    val nextKey: Int?
): Parcelable
