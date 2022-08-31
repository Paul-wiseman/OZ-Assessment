package com.example.ozeassessment.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ozeassessment.data.database.dao.FavoriteUsersDao
import com.example.ozeassessment.data.database.dao.UserDao
import com.example.ozeassessment.data.database.dao.UsersRemoteKeyDao
import com.example.ozeassessment.data.database.entity.FavoriteUserEntity
import com.example.ozeassessment.data.database.entity.User
import com.example.ozeassessment.data.database.entity.UsersRemoteKey

@Database(
    entities = [FavoriteUserEntity::class, User::class, UsersRemoteKey::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GitHubUsersTypeConverter::class)
abstract class GitHubUsersDatabase: RoomDatabase() {
    abstract fun usersDao(): UserDao
    abstract fun userRemoteKeyDao(): UsersRemoteKeyDao
    abstract fun favoriteUsersDao(): FavoriteUsersDao
}