package com.madness.hobbymatcher.networking.interceptors

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class CredentialsStore(context: Context) {
    private companion object {
        const val SECURE_PREFS_KEY = "cred_secure_prefs"
        const val TOKEN_KEY = "token_key"
        const val USERNAME_KEY = "username_key"
    }

    private var preferences: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        preferences = EncryptedSharedPreferences.create(
            context.applicationContext,
            SECURE_PREFS_KEY, masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var token: String
        get() = preferences.getString(TOKEN_KEY, "").orEmpty()
        set(value) = with(preferences.edit()) {
            if (value.isNotEmpty()) {
                putString(TOKEN_KEY, value)
                apply()
            } else {
                eraseToken()
            }
        }

    var username: String
        get() = preferences.getString(USERNAME_KEY, "").orEmpty()
        set(value) = with(preferences.edit()) {
            if (value.isNotEmpty()) {
                putString(USERNAME_KEY, value)
                apply()
            } else {
                eraseUsername()
            }
        }

    val hasToken: Boolean
        get() = preferences.contains(TOKEN_KEY)

    fun eraseToken() {
        with(preferences.edit()) {
            remove(TOKEN_KEY)
            apply()
        }
    }

    fun eraseUsername() {
        with(preferences.edit()) {
            remove(USERNAME_KEY)
            apply()
        }
    }
}