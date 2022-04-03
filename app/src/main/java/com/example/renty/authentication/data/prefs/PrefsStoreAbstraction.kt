package com.example.renty.authentication.data.prefs

import kotlinx.coroutines.flow.Flow

interface PrefsStoreAbstraction {


    fun getId(): Flow<String?>
    suspend fun setId(id: String)

    //suspend fun clearDataStore()


}