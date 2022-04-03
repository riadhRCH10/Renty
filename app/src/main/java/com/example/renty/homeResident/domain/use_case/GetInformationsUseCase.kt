package com.example.renty.homeResident.domain.use_case

import com.example.renty.core.utils.Resource
import com.example.renty.homeResident.domain.homeResidentRepository
import com.example.renty.homeResident.Dto.getInformationsDto
import kotlinx.coroutines.flow.Flow

class GetInformationsUseCase(
    private val repository: homeResidentRepository
) {
    operator fun invoke(token: String) : Flow<Resource<getInformationsDto>> {
        return repository.getInformations(token)
    }
}