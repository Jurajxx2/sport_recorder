package com.example.sportrecorder.model.remote

import java.io.Serializable

class PostLoginRequest(
    val email: String?,
    val password: String?,
    val returnSecureToken: Boolean = true
): Serializable