package com.example.renty.authentication.domain.use_case

import com.example.renty.authentication.data.remote.dto.loginResponseDto
import com.example.renty.authentication.domain.model.phoneVerificationRequest
import com.example.renty.authentication.domain.repository.AuthenticationRepository
import com.example.renty.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class PhoneVerificationUseCase(
    private val repository: AuthenticationRepository
) {

    operator fun invoke(body : phoneVerificationRequest) : Flow<Resource<String>> {
        return repository.phoneVerification(body)
    }

}