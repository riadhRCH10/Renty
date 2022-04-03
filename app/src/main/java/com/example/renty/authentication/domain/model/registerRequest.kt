package com.example.renty.authentication.domain.model

data class registerRequest(
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val phone_number: String
)