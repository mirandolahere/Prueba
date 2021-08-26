package com.application.koin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configurations")
data class Configurations(
    var username : String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}