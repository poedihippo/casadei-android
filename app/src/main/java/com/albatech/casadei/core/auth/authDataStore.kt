package com.albatech.casadei.core.auth

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val AUTH_PREFS_NAME = "auth_prefs"

val Context.authDataStore by preferencesDataStore(
    name = AUTH_PREFS_NAME
)

object AuthPrefsKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
}
