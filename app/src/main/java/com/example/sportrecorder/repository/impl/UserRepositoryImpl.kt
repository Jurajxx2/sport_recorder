package com.example.sportrecorder.repository.impl

import com.example.sportrecorder.api.Endpoints
import com.example.sportrecorder.api.service.UserService
import com.example.sportrecorder.database.dao.UserDao
import com.example.sportrecorder.helpers.EncryptedPrefs
import com.example.sportrecorder.helpers.Resource
import com.example.sportrecorder.helpers.networkBoundResource
import com.example.sportrecorder.model.User
import com.example.sportrecorder.model.remote.PostLoginRequest
import com.example.sportrecorder.model.remote.PostRefreshTokenRequest
import com.example.sportrecorder.model.remote.PostRegisterRequest
import com.example.sportrecorder.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class UserRepositoryImpl(private val userDao: UserDao, private val userService: UserService, private val apiKey: String) :
    UserRepository {

    override fun login(email: String?, password: String?): Flow<Resource<User?>> {
        return networkBoundResource(
            query = {
                userDao.loadUser().map { it?.toUser() }
            },
            fetch = {
                userService.login(PostLoginRequest(email, password), Endpoints.BASE_URL_AUTH + Endpoints.BASE_URL_AUTH_LOGIN_ENDPOINT, apiKey)
            },
            saveFetchResult = {
                EncryptedPrefs.setToken(it.idToken)
                EncryptedPrefs.setRefreshToken(it.refreshToken)
                EncryptedPrefs.setUserId(it.localId)
                userDao.deleteAll()
                userDao.insert(it.toLocal())
            }
        )
    }

    override fun register(email: String?, password: String?): Flow<Resource<User?>> {
        return networkBoundResource(
            query = {
                userDao.loadUser().map { it?.toUser() }
            },
            fetch = {
                userService.register(PostRegisterRequest(email, password), Endpoints.BASE_URL_AUTH + Endpoints.BASE_URL_AUTH_REGISTER_ENDPOINT, apiKey)
            },
            saveFetchResult = {
                EncryptedPrefs.setToken(it.idToken)
                EncryptedPrefs.setRefreshToken(it.refreshToken)
                EncryptedPrefs.setUserId(it.localId)
                userDao.deleteAll()
                userDao.insert(it.toLocal())
            }
        )
    }

    override suspend fun refreshToken() {
        val result = userService.refreshToken(PostRefreshTokenRequest(EncryptedPrefs.getRefreshToken()),Endpoints.BASE_URL_AUTH + Endpoints.BASE_URL_AUTH_REFRESH_TOKEN_ENDPOINT, apiKey)
        EncryptedPrefs.setToken(result.id_token)
        EncryptedPrefs.setRefreshToken(result.refresh_token)
    }

    override fun getUser() = userDao.loadUser().map { it?.toUser() }

    override suspend fun deleteUser() {
        userDao.deleteAll()
    }
}