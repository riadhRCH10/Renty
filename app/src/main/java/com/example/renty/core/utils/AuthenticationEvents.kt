package com.example.renty.core.utils

import com.example.renty.authentication.data.remote.dto.loginResponseDto

sealed class AuthenticationEvent {
    class Success(val resultText : String, val data: loginResponseDto?=null) : AuthenticationEvent()
    class Failure(val errorText : String,val errorCode: Int?=0) : AuthenticationEvent()
    object Loading : AuthenticationEvent()
    object Empty : AuthenticationEvent()
}