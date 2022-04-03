package com.example.renty.homeResident.Dto

data class getInformationsDto(
    val appartement: Appartement,
    val payed: Boolean,
    val roomates: List<Roomate>,
    val user: UserX
)