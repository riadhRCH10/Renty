package com.example.renty.homeResident.domain

import com.example.renty.core.utils.Resource
import com.example.renty.homeResident.Dto.getInformationsDto
import kotlinx.coroutines.flow.Flow

interface homeResidentRepository {

    fun getInformations(token: String) : Flow<Resource<getInformationsDto>>

}