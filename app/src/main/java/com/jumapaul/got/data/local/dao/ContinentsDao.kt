package com.jerimkaura.got.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jerimkaura.got.data.local.entities.Continent
import kotlinx.coroutines.flow.Flow

@Dao
interface ContinentsDao {
    @Query("SELECT * FROM continents")
    fun getContinents(): Flow<List<Continent>>

    @Query("SELECT * FROM continents WHERE id =:id")
    fun getContinentById(id: Int): LiveData<Continent>

    @Query("DELETE FROM characters")
    suspend fun deleteAllContinents()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManyContinents(continents: List<Continent>)
}