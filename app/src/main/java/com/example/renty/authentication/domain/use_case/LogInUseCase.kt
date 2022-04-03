package com.example.renty.authentication.domain.use_case

import com.example.renty.authentication.data.remote.dto.loginResponseDto
import com.example.renty.authentication.domain.model.loginRequest
import com.example.renty.authentication.domain.repository.AuthenticationRepository
import com.example.renty.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class LogInUseCase(
    private val repo : AuthenticationRepository
) {
    operator fun invoke(body: loginRequest) : Flow<Resource<loginResponseDto>> {
        return repo.Login(body)
    }
}