package com.example.renty.authentication.domain.repository

import com.example.renty.authentication.data.remote.dto.loginResponseDto
import com.example.renty.authentication.domain.model.loginRequest
import com.example.renty.authentication.domain.model.phoneVerificationRequest
import com.example.renty.authentication.domain.model.registerRequest
import com.example.renty.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun Login (payload: loginRequest) : Flow<Resource<loginResponseDto>>

    fun phoneVerification(payload: phoneVerificationRequest) : Flow<Resource<String>>

    fun register(payload: registerRequest) : Flow<Resource<String>>

}