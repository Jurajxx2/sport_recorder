package com.example.sportrecorder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sportrecorder.database.dao.SportRecordDao
import com.example.sportrecorder.database.dao.UserDao
import com.example.sportrecorder.model.local.LocalSportRecord
import com.example.sportrecorder.model.local.LocalUser

@Database(entities = [LocalSportRecord::class, LocalUser::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sportRecordDao(): SportRecordDao

    companion object {

        private const val DATABASE_NAME = "sport_records_database"
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}