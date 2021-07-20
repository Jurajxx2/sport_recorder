package com.example.sportrecorder.database.dao

import androidx.room.*
import com.example.sportrecorder.model.local.LocalUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id=:id")
    fun loadUser(id: String): Flow<LocalUser?>

    @Query("SELECT * FROM user")
    fun loadUser(): Flow<LocalUser?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: LocalUser)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}