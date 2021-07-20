package com.example.sportrecorder.repository

import com.example.sportrecorder.R
import com.example.sportrecorder.api.Endpoints
import com.example.sportrecorder.api.apiModule
import com.example.sportrecorder.api.service.SportRecordService
import com.example.sportrecorder.api.service.UserService
import com.example.sportrecorder.database.AppDatabase
import com.example.sportrecorder.database.dao.SportRecordDao
import com.example.sportrecorder.database.dao.UserDao
import com.example.sportrecorder.database.databaseModule
import com.example.sportrecorder.repository.impl.SportRecordRepositoryImpl
import com.example.sportrecorder.repository.impl.UserRepositoryImpl
import com.example.sportrecorder.screens.login.LoginViewModel
import com.example.sportrecorder.screens.main.MainViewModel
import com.example.sportrecorder.screens.register.RegisterViewModel
import com.example.sportrecorder.screens.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    loadKoinModules(listOf(databaseModule, apiModule))
    single<SportRecordRepository> { SportRecordRepositoryImpl(get(), get(), get(), androidContext().getString(R.string.myApiKey)) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), androidContext().getString(R.string.myApiKey)) }
}