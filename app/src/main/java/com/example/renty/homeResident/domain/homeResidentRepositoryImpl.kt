package com.example.renty.homeResident.domain

import com.example.renty.authentication.data.remote.AuthenticationApi
import com.example.renty.core.utils.Resource
import com.example.renty.homeResident.Dto.getInformationsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class homeResidentRepositoryImpl(
    private val Api : AuthenticationApi
) : homeResidentRepository {

    override fun getInformations(token: String): Flow<Resource<getInformationsDto>> = flow {
        emit(Resource.Loading())
        try{
            val remoteInfos = Api.getMyInfo(token)
            emit(Resource.Success(remoteInfos))
        } catch (e: HttpException) {
            emit(Resource.Error("http exception: failed ${e.message()}",e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed to reach server ${e.message}"))
        }
    }
}