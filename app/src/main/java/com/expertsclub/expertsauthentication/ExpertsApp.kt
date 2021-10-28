package com.expertsclub.expertsauthentication

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class ExpertsApp : Application() {

    val authDataStore: DataStore<Preferences> by preferencesDataStore(name = "authStore")
    val localDataStore: DataStore<Preferences> by preferencesDataStore(name = "localStore")
}