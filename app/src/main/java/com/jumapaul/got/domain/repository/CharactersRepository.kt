package com.jerimkaura.got.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.withTransaction
import com.jerimkaura.got.data.local.ThronesDatabase
import com.jerimkaura.got.data.local.dao.CharacterDao
import com.jerimkaura.got.data.local.entities.Character
import com.jerimkaura.got.data.remote.ThronesApi
import com.jerimkaura.got.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: ThronesApi,
    private val characterDao: CharacterDao,
    private val database: ThronesDatabase
) {
    fun getCharacters() = networkBoundResource(
        query = {
            characterDao.getAllCharacters()
        },
        fetch = {
            delay(2000)
            api.getAllCharacters()
        },
        saveFetchResult = { characters ->
            database.withTransaction {
                characterDao.deleteAllCharacters()
                characterDao.insertManyCharacters(characters)
            }
        }
    )

    fun getCharacterById(id: Int): LiveData<Character> {
        return characterDao.getCharacterById(id)
    }
}