package com.example.sportrecorder.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportrecorder.model.User

@Entity(tableName = "user")
class LocalUser(
    @PrimaryKey val id: String,
    val email: String
) {
    fun toUser() = User(id,email)
}