package com.application.koin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photos(
    var url : String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}