package com.example.sportrecorder.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sportrecorder.model.local.LocalSportRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface SportRecordDao {

    @Query("SELECT * FROM sport_record")
    fun getAll(): Flow<List<LocalSportRecord>>

    @Query("SELECT * FROM sport_record WHERE id=:recordId")
    fun loadById(recordId: String): Flow<LocalSportRecord>

    @Insert
    suspend fun insertAll(vararg records: LocalSportRecord)

    @Delete
    suspend fun delete(message: LocalSportRecord)

    @Query("DELETE FROM sport_record")
    suspend fun deleteAll()
}