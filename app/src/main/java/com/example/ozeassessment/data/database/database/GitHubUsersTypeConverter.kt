package com.example.ozeassessment.data.database.database

import androidx.room.TypeConverter
import com.example.ozeassessment.models.Item
import com.example.ozeassessment.models.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GitHubUsersTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun usersToString(users: Users): String {
        return gson.toJson(users)
    }

    @TypeConverter
    fun stringToUsers(data: String): Users {
        val listType = object : TypeToken<Users>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(item: Item): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToResult(data: String): Item {
        val listType = object : TypeToken<Item>() {}.type
        return gson.fromJson(data, listType)
    }

}