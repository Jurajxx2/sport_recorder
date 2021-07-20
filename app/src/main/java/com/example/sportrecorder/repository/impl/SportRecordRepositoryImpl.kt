package com.example.sportrecorder.repository.impl

import com.example.sportrecorder.api.Endpoints
import com.example.sportrecorder.api.service.SportRecordService
import com.example.sportrecorder.api.service.UserService
import com.example.sportrecorder.database.dao.SportRecordDao
import com.example.sportrecorder.helpers.EncryptedPrefs
import com.example.sportrecorder.helpers.Resource
import com.example.sportrecorder.helpers.networkBoundResource
import com.example.sportrecorder.model.SportRecord
import com.example.sportrecorder.model.User
import com.example.sportrecorder.model.remote.PostLoginRequest
import com.example.sportrecorder.model.remote.PostRefreshTokenRequest
import com.example.sportrecorder.repository.SportRecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SportRecordRepositoryImpl(
    private val sportRecordDao: SportRecordDao,
    private val sportRecordService: SportRecordService,
    private val userService: UserService,
    private val apiKey: String
) : SportRecordRepository {

    override fun createSportRecord(sportRecord: SportRecord): Flow<Resource<SportRecord?>> {
        return flow<Resource<SportRecord>> {
            emit(Resource.loading(null))
            if (EncryptedPrefs.shouldVerify()) {
                refreshToken()
            }

            val result = try {
                when (sportRecord.storageType) {
                    SportRecord.StorageType.Local -> {
                        sportRecordDao.insertAll(sportRecord.toLocalSportRecord())
                    }
                    SportRecord.StorageType.Remote -> {
                        sportRecordService.createRecord(sportRecord.toRemoteSportRecord())
                    }
                    else -> {
                        sportRecordDao.insertAll(sportRecord.toLocalSportRecord())
                        sportRecordService.createRecord(sportRecord.toRemoteSportRecord())
                    }
                }
                Resource.success(null)
            } catch (throwable: Throwable) {
                Resource.error(throwable, null)
            }

            emit(result)
        }
    }

    override fun getSportRecords(): Flow<Resource<List<SportRecord>?>> {
        return flow<Resource<List<SportRecord>>> {
            emit(Resource.loading(null))
            if (EncryptedPrefs.shouldVerify()) {
                refreshToken()
            }

            val result = try {
                val resultRemote = try {
                    sportRecordService.getRecords()?.map { it.value.toSportRecord() } ?: emptyList()
                } catch (e: NullPointerException) {
                    emptyList()
                }
                val resultLocal = sportRecordDao.getAll().first().map { it.toSportRecord() }

                Resource.success(resultLocal.plus(resultRemote).sortedBy { it.id })
            } catch (throwable: Throwable) {
                Resource.error(throwable, null)
            }

            emit(result)
        }
    }

    override fun updateSportRecord(sportRecord: SportRecord): Flow<Resource<SportRecord?>> {
        return flow<Resource<SportRecord>> {
            emit(Resource.loading(null))
            if (EncryptedPrefs.shouldVerify()) {
                refreshToken()
            }

            val result = try {
                when (sportRecord.storageType) {
                    SportRecord.StorageType.Local -> {
                        sportRecordDao.insertAll(sportRecord.toLocalSportRecord())
                        sportRecordService.createRecord(sportRecord.toRemoteSportRecord())
                    }
                    SportRecord.StorageType.Remote -> {
                        sportRecordService.createRecord(sportRecord.toRemoteSportRecord())
                    }
                    else -> {
                        sportRecordDao.insertAll(sportRecord.toLocalSportRecord())
                        sportRecordService.createRecord(sportRecord.toRemoteSportRecord())
                    }
                }
                Resource.success(null)
            } catch (throwable: Throwable) {
                Resource.error(throwable, null)
            }

            emit(result)
        }
    }

    override suspend fun deleteSportRecord(recordId: String, sportRecord: SportRecord) {
        flow<Resource<SportRecord>> {
            emit(Resource.loading(null))
            if (EncryptedPrefs.shouldVerify()) {
                refreshToken()
            }

            val result = try {
                when (sportRecord.storageType) {
                    SportRecord.StorageType.Local -> {
                        sportRecordDao.insertAll(sportRecord.toLocalSportRecord())
                    }
                    SportRecord.StorageType.Remote -> {
                        sportRecordService.createRecord(sportRecord.toRemoteSportRecord())
                    }
                    else -> {
                        sportRecordDao.insertAll(sportRecord.toLocalSportRecord())
                        sportRecordService.createRecord(sportRecord.toRemoteSportRecord())
                    }
                }
                Resource.success(null)
            } catch (throwable: Throwable) {
                Resource.error(throwable, null)
            }

            emit(result)
        }
    }

    override suspend fun refreshToken() {
        val result = userService.refreshToken(PostRefreshTokenRequest(EncryptedPrefs.getRefreshToken()),Endpoints.BASE_URL_AUTH + Endpoints.BASE_URL_AUTH_REFRESH_TOKEN_ENDPOINT, apiKey)
        EncryptedPrefs.setToken(result.id_token)
        EncryptedPrefs.setRefreshToken(result.refresh_token)
    }

    override suspend fun deleteAll() {
        sportRecordDao.deleteAll()
    }
}