package com.example.sportrecorder.api

import com.example.sportrecorder.api.service.SportRecordService
import com.example.sportrecorder.api.service.UserService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL_DATABASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<UserService> { get<Retrofit>().create(UserService::class.java) }
    single<SportRecordService> { get<Retrofit>().create(SportRecordService::class.java) }
}