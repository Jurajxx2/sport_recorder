package com.example.sportrecorder.model

import com.example.sportrecorder.model.local.LocalSportRecord
import com.example.sportrecorder.model.remote.RemoteSportRecord

data class SportRecord(
    val id: String,
    val name: String?,
    val place: Place?,
    val duration: String?,
    val storageType: StorageType?,
) {
    data class Place(
        val name: String?,
        val latitude: Double?,
        val longitude: Double?
    ) {
        fun toRemotePlace() = RemoteSportRecord.Place(name, latitude, longitude)
        fun toLocalPlace() = LocalSportRecord.Place(name, latitude, longitude)
    }

    enum class StorageType{
        Local,
        Remote
    }

    fun toRemoteSportRecord() = RemoteSportRecord(id,name,place?.toRemotePlace(),duration,storageType?.name)
    fun toLocalSportRecord() = LocalSportRecord(id,name,place?.toLocalPlace(),duration,storageType?.name)
}