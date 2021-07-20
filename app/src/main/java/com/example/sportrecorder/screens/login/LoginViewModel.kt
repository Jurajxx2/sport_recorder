package com.example.sportrecorder.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportrecorder.api.service.SportRecordService
import com.example.sportrecorder.api.service.UserService
import com.example.sportrecorder.helpers.Resource
import com.example.sportrecorder.helpers.Status
import com.example.sportrecorder.model.User
import com.example.sportrecorder.model.remote.PostLoginRequest
import com.example.sportrecorder.model.remote.PostRegisterRequest
import com.example.sportrecorder.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val success = MutableLiveData(false)

    val loading = MutableLiveData(false)
    val error = MutableLiveData<String>()

    val user = MutableLiveData<User?>()


    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.login(email.value, password.value).catch {
                Log.d("RECORDERX", it.stackTraceToString())
            }.collect {
                when(it.status) {
                    Status.LOADING -> loading.postValue(true)
                    Status.ERROR -> error.postValue("There was an error while signing in. Please try again later or contact our support.")
                    Status.SUCCESS -> user.postValue(it.data)
                }
            }
        }
    }
}