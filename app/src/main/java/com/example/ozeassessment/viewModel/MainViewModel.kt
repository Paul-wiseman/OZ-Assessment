package com.example.ozeassessment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.ozeassessment.models.Item
import com.example.ozeassessment.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repositoryImpl: RepositoryImpl) :
    ViewModel() {


    val users: Flowable<PagingData<Item>> = repositoryImpl.getGitHubUser()

    fun getUser(): Flowable<PagingData<Item>> {
        return repositoryImpl
            .getGitHubUser()
            .cachedIn(viewModelScope)
    }

}