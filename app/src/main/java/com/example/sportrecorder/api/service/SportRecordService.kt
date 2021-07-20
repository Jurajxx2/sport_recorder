package com.example.sportrecorder.api.service

import com.example.sportrecorder.helpers.EncryptedPrefs
import com.example.sportrecorder.model.remote.RemoteSportRecord
import retrofit2.http.*

interface SportRecordService {

    @PUT("rentals/{userId}/{id}.json")
    suspend fun updateRecord(@Path("id") rentalId: String, @Body body: RemoteSportRecord, @Path("userId") userId: String? = EncryptedPrefs.getUserId(), @Query("apiKey") apiKey: String? = EncryptedPrefs.getToken()): RemoteSportRecord

    @GET("rentals/{userId}.json")
    suspend fun getRecords(@Path("userId") userId: String? = EncryptedPrefs.getUserId(), @Query("apiKey") apiKey: String? = EncryptedPrefs.getToken()): HashMap<String, RemoteSportRecord>?

    @GET("rentals/{userId}/{id}.json")
    suspend fun getRecordById(@Path("id") rentalId: String, @Path("userId") userId: String? = EncryptedPrefs.getUserId(), @Query("apiKey") apiKey: String? = EncryptedPrefs.getToken()): RemoteSportRecord

    @POST("rentals/{userId}.json")
    suspend fun createRecord(@Body body: RemoteSportRecord, @Path("userId") userId: String? = EncryptedPrefs.getUserId(), @Query("apiKey") apiKey: String? = EncryptedPrefs.getToken()): RemoteSportRecord

    @DELETE("rentals/{userId}/{id}.json")
    suspend fun deleteRecord(@Path("id") rentalId: String, @Path("userId") userId: String? = EncryptedPrefs.getUserId(), @Query("apiKey") apiKey: String? = EncryptedPrefs.getToken()): RemoteSportRecord

}