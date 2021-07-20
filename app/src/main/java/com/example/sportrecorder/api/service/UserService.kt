package com.example.sportrecorder.api.service

import com.example.sportrecorder.model.remote.*
import retrofit2.http.*

interface UserService {

    @Headers("Accept: application/json")
    @POST
    suspend fun login(@Body data: PostLoginRequest, @Url url: String, @Query("key") key: String): PostLoginResponse

    @Headers("Accept: application/json")
    @POST
    suspend fun register(@Body data: PostRegisterRequest, @Url url: String, @Query("key") key: String): PostRegisterResponse

    @Headers("Accept: application/json")
    @POST
    suspend fun refreshToken(@Body data: PostRefreshTokenRequest, @Url url: String, @Query("key") key: String): PostRefreshTokenResponse
}