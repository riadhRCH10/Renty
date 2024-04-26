package com.example.renty.authentication.data.repository

import android.util.Log
import com.example.renty.authentication.data.remote.AuthenticationApi
import com.example.renty.authentication.data.remote.dto.loginResponseDto
import com.example.renty.authentication.domain.model.loginRequest
import com.example.renty.authentication.domain.model.phoneVerificationRequest
import com.example.renty.authentication.domain.model.registerRequest
import com.example.renty.authentication.domain.repository.AuthenticationRepository
import com.example.renty.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthenticationRepositoryImpl(
    private val Api : AuthenticationApi
) : AuthenticationRepository {

    override fun Login(payload: loginRequest): Flow<Resource<loginResponseDto>> = flow {

        emit(Resource.Loading())

        try {
            val remoteUser = Api.login(payload)
            emit(Resource.Success(remoteUser))
        } catch (e: HttpException) {
            Log.d("auth", "failed" + e.toString())
            emit(Resource.Error("failed to authenticate: ${e.message()} \ncode: ${e.code()}", e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed to reach server ${e.message}"))
        }

    }

    override fun phoneVerification(payload: phoneVerificationRequest): Flow<Resource<String>> = flow {

        emit(Resource.Loading())
        try {
            val result = Api.phoneVerification(payload)
            emit(Resource.Success("Phone verified ! $result"))
        } catch (e: HttpException) {
            Log.d("auth", "failed" + e.toString())
            emit(Resource.Error("failed to verify: ${e.message()} \ncode: ${e.code()}", e.code()))
        } catch (e: IOException) {
            Log.d("auth", "failed IO" + e.toString())
            emit(Resource.Error("failed to reach server ${e.message}"))
        }

    }

    override fun register(payload: registerRequest): Flow<Resource<String>> = flow {

        emit(Resource.Loading())
        try {
            val result = Api.register(payload)
            emit(Resource.Success("registered !"))
        } catch (e: HttpException) {
            emit(Resource.Error("failed to register: ${e.message()} \ncode: ${e.code()}", e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed to reach server ${e.message}"))
        }

    }
}