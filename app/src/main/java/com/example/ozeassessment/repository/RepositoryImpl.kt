package com.example.ozeassessment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.ozeassessment.data.datasource.GithubPagingSource
import com.example.ozeassessment.data.network.apiservice.GitHubUserApi
import com.example.ozeassessment.models.Item
import io.reactivex.Flowable
import javax.inject.Inject

private const val PAGE_SIZE = 30

class RepositoryImpl @Inject constructor(
    private val gitHubUserApi: GitHubUserApi,
) {
    fun getGitHubUser(): Flowable<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { GithubPagingSource(gitHubUserApi) }
        ).flowable
    }


}