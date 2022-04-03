package com.example.renty.authentication.data.remote.dto

data class loginResponseDto(
    val token: Token,
    val user_type: String,
    val username: String
)