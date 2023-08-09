package com.jerimkaura.got.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "continents")
data class Continent(
    @PrimaryKey
    var id: Int,
    var name: String
)
