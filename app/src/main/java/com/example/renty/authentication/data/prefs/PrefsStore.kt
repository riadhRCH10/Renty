package com.example.renty.authentication.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("user_prefs")

class PrefsStore @Inject constructor(appContext: Context) : PrefsStoreAbstraction {

    private val dataStore = appContext.dataStore


    override fun getId() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.USER_ID] }


    override suspend fun setId(id: String) {
        dataStore.edit {
            it[PreferencesKeys.USER_ID] = id
        }
    }


    private object PreferencesKeys {
        val USER_ID = stringPreferencesKey("id")

    }
}