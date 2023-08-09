package com.jerimkaura.got.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character (
    @PrimaryKey
    var id: Int = 0,
    var firstName: String? = "",
    var lastName: String? = "",
    var fullName: String? = "",
    var title: String? = "",
    var family: String? = "",
    var image: String? = "",
    var imageUrl: String? = ""
)