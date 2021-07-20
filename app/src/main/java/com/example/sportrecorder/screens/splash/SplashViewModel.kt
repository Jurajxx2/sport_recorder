package com.example.sportrecorder.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.sportrecorder.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class SplashViewModel(private val userRepository: UserRepository): ViewModel() {

    val user = liveData {
        userRepository.getUser().catch {
            emit(null)
        }.collect {
            emit(it)
        }
    }
}