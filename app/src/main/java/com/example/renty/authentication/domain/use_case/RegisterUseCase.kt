package com.example.renty.authentication.domain.use_case

import com.example.renty.authentication.domain.model.registerRequest
import com.example.renty.authentication.domain.repository.AuthenticationRepository
import com.example.renty.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
    private val repository: AuthenticationRepository
) {

    operator fun invoke(payload : registerRequest) : Flow<Resource<String>> {
        return repository.register(payload)
    }
}