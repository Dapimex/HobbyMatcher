package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.Profile.ProfileFragment
import com.madness.hobbymatcher.loginmanager.di.LoginModule
import com.madness.hobbymatcher.loginmanager.view.LoginActivity
import com.madness.hobbymatcher.loginmanager.view.RegistrationActivity
import com.madness.hobbymatcher.networking.di.NetworkingModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkingModule::class,
        LoginModule::class,
        AndroidInjectionModule::class,
        LoginActivityModule::class,
        RegistrationActivityModule::class
    ]
)
interface ApplicationComponent {
    fun inject(application: HobbyMatcherApplication)

    fun inject(profileFragment: ProfileFragment)

    fun inject(loginActivity: LoginActivity)

    fun inject(registrationActivity: RegistrationActivity)
}