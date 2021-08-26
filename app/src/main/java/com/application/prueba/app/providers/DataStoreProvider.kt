package com.application.prueba.app.providers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.application.prueba.app.util.addToLocalStorage
import com.application.prueba.app.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun provideDataStoreInstance(applicationContext : Context ) : DataStore<Preferences>{

    return applicationContext.dataStore

}

class DataStoreProvider(private val dataStore:DataStore<Preferences>) {

    val accessToken: Flow<String>
    get() = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN]?: ""
    }

    val onboardingCompleted: Flow<Boolean>
    get() = dataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED]?: false
    }

    suspend fun saveAccessToken(accessToken: String) {
        dataStore.addToLocalStorage {
            this[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun completeOnboardingTutorial() {
        dataStore.addToLocalStorage {
            this[ONBOARDING_COMPLETED] = true
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

}