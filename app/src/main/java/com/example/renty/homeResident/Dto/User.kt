package com.example.renty.homeResident.Dto

data class User(
    val email: String,
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val user_type: Int,
    val username: String
)