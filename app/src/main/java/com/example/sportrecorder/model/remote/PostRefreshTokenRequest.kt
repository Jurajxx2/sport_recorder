package com.example.sportrecorder.model.remote

import java.io.Serializable

class PostRefreshTokenRequest(
    val refresh_token: String?,
    val grant_type: String = "refresh_token"
    ): Serializable