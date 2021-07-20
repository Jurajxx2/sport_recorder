package com.example.sportrecorder.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportrecorder.model.SportRecord

@Entity(tableName = "sport_record")
data class LocalSportRecord(
    @PrimaryKey val id: String,
    val name: String?,
    val place: Place?,
    val duration: String?,
    val storageType: String?,
) {
    data class Place(
        val name: String?,
        val latitude: Double?,
        val longitude: Double?
    ) {
        fun toSportRecordPlace() = SportRecord.Place(name,latitude, longitude)
    }

    fun toSportRecord() = SportRecord(id, name, place?.toSportRecordPlace(), duration,
        storageType?.let { SportRecord.StorageType.valueOf(it) })
}