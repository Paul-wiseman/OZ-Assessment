package com.example.ozeassessment.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val userId: Int,
    @SerializedName("avatar_url")
    val avatar_url: String,
    @SerializedName("events_url")
    val events_url: String,
    @SerializedName("followers_url")
    val followers_url: String,
    @SerializedName("following_url")
    val following_url: String,
    @SerializedName("gists_url")
    val gists_url: String,
    @SerializedName("gravatar_id")
    val gravatar_id: String,
    @SerializedName("html_url")
    val html_url: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("node_id")
    val node_id: String,
    @SerializedName("organizations_url")
    val organizations_url: String,
    @SerializedName("received_events_url")
    val received_events_url: String,
    @SerializedName("repos_url")
    val repos_url: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("site_admin")
    val site_admin: Boolean,
    @SerializedName("starred_url")
    val starred_url: String,
    @SerializedName("subscriptions_url")
    val subscriptions_url: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
): Parcelable
