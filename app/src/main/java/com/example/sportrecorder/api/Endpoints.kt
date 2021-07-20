package com.example.sportrecorder.api

object Endpoints {
    /*const val BASE_URL_AUTH = "https://identitytoolkit.googleapis.com/v1"
    const val BASE_URL_AUTH_LOGIN_ENDPOINT = "/accounts:signInWithPassword"
    const val BASE_URL_AUTH_REGISTER_ENDPOINT = ""*/
    const val BASE_URL_AUTH = "https://identitytoolkit.googleapis.com"
    const val BASE_URL_AUTH_LOGIN_ENDPOINT = "/v1/accounts:signInWithPassword"
    const val BASE_URL_AUTH_REGISTER_ENDPOINT = "/v1/accounts:signUp"
    const val BASE_URL_AUTH_REFRESH_TOKEN_ENDPOINT = "/v1/token"
    const val BASE_URL_DATABASE = "https://sportrecorder-307ee-default-rtdb.europe-west1.firebasedatabase.app/"
}