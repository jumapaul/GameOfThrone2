package com.jerimkaura.got.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jerimkaura.got.data.local.dao.CharacterDao
import com.jerimkaura.got.data.local.dao.ContinentsDao
import com.jerimkaura.got.data.local.entities.Character
import com.jerimkaura.got.data.local.entities.Continent

@Database(entities = [Character::class, Continent::class], version = 1, exportSchema = false)
abstract class ThronesDatabase: RoomDatabase(){
    abstract fun characterDao(): CharacterDao
    abstract fun continentsDao(): ContinentsDao
}
