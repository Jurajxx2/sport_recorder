package com.example.sportrecorder.model.remote

import com.example.sportrecorder.model.local.LocalUser
import java.io.Serializable

class PostRegisterResponse(
    val localId: String,
    val idToken: String,
    val email: String,
    val refreshToken: String
): Serializable {
    fun toLocal() = LocalUser(localId, email)
}