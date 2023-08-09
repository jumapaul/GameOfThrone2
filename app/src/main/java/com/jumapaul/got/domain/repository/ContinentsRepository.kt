package com.jerimkaura.got.domain.repository

import androidx.room.withTransaction
import com.jerimkaura.got.data.local.ThronesDatabase
import com.jerimkaura.got.data.local.dao.ContinentsDao
import com.jerimkaura.got.data.remote.ThronesApi
import com.jerimkaura.got.util.networkBoundResource
import javax.inject.Inject

class ContinentsRepository @Inject constructor(
    private val api: ThronesApi,
    private val continentsDao: ContinentsDao,
    private val database: ThronesDatabase
) {
    fun getContinents() = networkBoundResource(
        query = {
            continentsDao.getContinents()
        },
        fetch = {
            api.getAllContinents()
        },
        saveFetchResult = { continents ->
            database.withTransaction {
                continentsDao.deleteAllContinents()
                continentsDao.insertManyContinents(continents)
            }
        }
    )
}