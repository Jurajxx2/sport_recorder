package com.example.sportrecorder.repository

import com.example.sportrecorder.helpers.Resource
import com.example.sportrecorder.helpers.networkBoundResource
import com.example.sportrecorder.model.User
import com.example.sportrecorder.model.local.LocalUser
import com.example.sportrecorder.model.remote.PostLoginRequest
import com.example.sportrecorder.model.remote.PostLoginResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(email: String?, password: String?): Flow<Resource<User?>>
    fun register(email: String?, password: String?): Flow<Resource<User?>>
    suspend fun refreshToken()
    fun getUser(): Flow<User?>
    suspend fun deleteUser()
}