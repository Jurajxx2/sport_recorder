package com.example.sportrecorder.database

import androidx.room.TypeConverter
import com.example.sportrecorder.model.local.LocalSportRecord
import com.google.gson.Gson

class Converters {
     @TypeConverter
     fun fromString(value: String): LocalSportRecord.Place {
         return Gson().fromJson(value, LocalSportRecord.Place::class.java)
     }

    @TypeConverter
    fun localPlaceToString(value: LocalSportRecord.Place): String {
        return Gson().toJson(value)
    }
}