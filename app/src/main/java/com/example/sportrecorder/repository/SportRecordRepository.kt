package com.example.sportrecorder.repository

import com.example.sportrecorder.helpers.Resource
import com.example.sportrecorder.model.SportRecord
import com.example.sportrecorder.model.User
import kotlinx.coroutines.flow.Flow

interface SportRecordRepository {

    fun createSportRecord(sportRecord: SportRecord): Flow<Resource<SportRecord?>>
    fun updateSportRecord(sportRecord: SportRecord): Flow<Resource<SportRecord?>>
    fun getSportRecords(): Flow<Resource<List<SportRecord>?>>
    suspend fun deleteSportRecord(recordId: String, sportRecord: SportRecord)
    suspend fun refreshToken()
    suspend fun deleteAll()
}