package com.madness.hobbymatcher.loginmanager.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class CredentialsStore(context: Context) {
    private companion object {
        const val SECURE_PREFS_KEY = "cred_secure_prefs"
        const val TOKEN_KEY = "token_key"
    }

    private var preferences: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        preferences = EncryptedSharedPreferences.create(
            context.applicationContext, SECURE_PREFS_KEY, masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var token: String
        get() = preferences.getString(TOKEN_KEY, "").orEmpty()
        set(value) = with(preferences.edit()) {
            putString(TOKEN_KEY, value)
            apply()
        }

    val hasToken: Boolean
        get() = preferences.contains(TOKEN_KEY)

}