package com.example.ozeassessment.models

import com.google.gson.annotations.SerializedName

data class Users(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val users: List<Item>,
    val total_count: Int
)