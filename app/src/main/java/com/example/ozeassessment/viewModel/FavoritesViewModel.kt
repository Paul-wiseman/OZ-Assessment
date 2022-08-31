package com.example.ozeassessment.viewModel

import androidx.lifecycle.ViewModel
import com.example.ozeassessment.data.database.entity.FavoriteUserEntity
import com.example.ozeassessment.repository.FavoriteUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val favoriteUsersRepository: FavoriteUsersRepository):ViewModel() {

    val readFavoriteUsersEntity = favoriteUsersRepository.readFavoriteUsersEntity

    fun readFavoritesFromDb(){
        favoriteUsersRepository.readFavoritesFromDb()
    }

    fun saveFavoriteUser(favoriteUserEntity: FavoriteUserEntity){
        favoriteUsersRepository.insertFavoriteUser(favoriteUserEntity)
    }

    fun deleteFavoriteUser(favoriteUserEntity: FavoriteUserEntity){
        favoriteUsersRepository.deleteFavoriteUser(favoriteUserEntity)
    }

    fun deleteAllFavoriteUser(){
        favoriteUsersRepository.deleteAllFavoriteUsers()
    }

    override fun onCleared() {
        favoriteUsersRepository.disposable.dispose()
        favoriteUsersRepository.disposable.clear()
        super.onCleared()
    }
}