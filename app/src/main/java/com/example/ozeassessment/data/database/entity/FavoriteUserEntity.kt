package com.example.ozeassessment.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ozeassessment.models.Item
import com.example.ozeassessment.util.Constants.FAVORITE_USER_TABLE

@Entity(tableName = FAVORITE_USER_TABLE)
class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val item: Item
)