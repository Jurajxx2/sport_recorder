package com.example.sportrecorder.database

import com.example.sportrecorder.api.Endpoints
import com.example.sportrecorder.api.service.SportRecordService
import com.example.sportrecorder.api.service.UserService
import com.example.sportrecorder.database.dao.SportRecordDao
import com.example.sportrecorder.database.dao.UserDao
import com.example.sportrecorder.repository.impl.SportRecordRepositoryImpl
import com.example.sportrecorder.repository.impl.UserRepositoryImpl
import com.example.sportrecorder.screens.login.LoginViewModel
import com.example.sportrecorder.screens.main.MainViewModel
import com.example.sportrecorder.screens.register.RegisterViewModel
import com.example.sportrecorder.screens.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {

    single<AppDatabase> { AppDatabase.getInstance(androidContext()) }

    single<UserDao> { get<AppDatabase>().userDao() }
    single<SportRecordDao> { get<AppDatabase>().sportRecordDao() }
}