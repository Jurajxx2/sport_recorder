package com.example.sportrecorder.screens.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportrecorder.api.service.UserService
import com.example.sportrecorder.helpers.Status
import com.example.sportrecorder.model.User
import com.example.sportrecorder.model.remote.PostRegisterRequest
import com.example.sportrecorder.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordConfirm = MutableLiveData("")

    val loading = MutableLiveData(false)
    val error = MutableLiveData<String>()
    val user = MutableLiveData<User?>()


    /*fun login() {
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://digilab.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(UserService::class.java)
                val body = PostRegisterRequest(email.value, password.value)
                val url = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/signupNewUser?key=${apiKey.value}"
                val result = service.register(body, url, "")
            } catch (e: Exception) {
                Log.d("RECORDERX", e.stackTraceToString())
            }
        }
    }
*/
    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.register(email.value, password.value).catch {
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