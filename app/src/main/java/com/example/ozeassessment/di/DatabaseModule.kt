package com.example.ozeassessment.di

import android.content.Context
import androidx.room.Room
import com.example.ozeassessment.data.database.database.GitHubUsersDatabase
import com.example.ozeassessment.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        GitHubUsersDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: GitHubUsersDatabase) = database.usersDao()

    @Singleton
    @Provides
    fun provideUserRemoteKeyDao(database: GitHubUsersDatabase) = database.userRemoteKeyDao()

    @Singleton
    @Provides
    fun provideFavoriteUserDao(database: GitHubUsersDatabase) = database.favoriteUsersDao()

    @Singleton
    @Provides
    fun provideDisposable() = CompositeDisposable()

}