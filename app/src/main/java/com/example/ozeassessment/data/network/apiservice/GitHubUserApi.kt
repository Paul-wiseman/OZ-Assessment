package com.example.ozeassessment.data.network.apiservice

import com.example.ozeassessment.models.Users
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubUserApi {

    // /users/
    @GET("search/users")
    fun getUsers(
        @Query("q") q: String = "lagos",
        @Query("page") page: Int,
    ): Single<Users>
}