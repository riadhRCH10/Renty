package com.example.renty.authentication.data.remote

import com.example.renty.authentication.data.remote.dto.loginResponseDto
import com.example.renty.authentication.data.remote.dto.serverResponceDto
import com.example.renty.authentication.domain.model.loginRequest
import com.example.renty.authentication.domain.model.phoneVerificationRequest
import com.example.renty.authentication.domain.model.registerRequest
import com.example.renty.homeResident.Dto.getInformationsDto
import retrofit2.http.*

interface AuthenticationApi {

    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: loginRequest) : loginResponseDto

    @POST("api/auth/check_phone_number")
    suspend fun phoneVerification(@Body VerificationRequest: phoneVerificationRequest) : serverResponceDto

    @POST("api/auth/register")
    suspend fun register(@Body body: registerRequest) : serverResponceDto

    @GET("api/auth/me")
    suspend fun getMyInfo(@Header("Authorization") authToken: String) : getInformationsDto


}