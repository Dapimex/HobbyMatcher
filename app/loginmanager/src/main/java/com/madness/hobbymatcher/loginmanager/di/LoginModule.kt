package com.madness.hobbymatcher.loginmanager.di

import android.content.Context
import com.madness.hobbymatcher.networking.interceptors.CredentialsStore
import com.madness.hobbymatcher.loginmanager.security.LoginManager
import com.madness.hobbymatcher.networking.AuthService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginModule {

    @Provides
    @Singleton
    fun providesLoginManager(
        context: Context,
        authService: AuthService,
        credentialsStore: CredentialsStore
    ) : LoginManager {
        return LoginManager(context, authService, credentialsStore)
    }
}