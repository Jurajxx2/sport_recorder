package com.example.sportrecorder.model.remote

import com.example.sportrecorder.model.SportRecord
import java.io.Serializable

class RemoteSportRecord(
    val id: String,
    val name: String?,
    val place: Place?,
    val duration: String?,
    val storageType: String?
): Serializable {

    class Place(
        val name: String?,
        val latitude: Double?,
        val longitude: Double?
    ): Serializable {
        fun toPlace() = SportRecord.Place(name, latitude, longitude)
    }

    fun toSportRecord() = SportRecord(id,name,place?.toPlace(),duration,
        storageType?.let { SportRecord.StorageType.valueOf(it) })
}