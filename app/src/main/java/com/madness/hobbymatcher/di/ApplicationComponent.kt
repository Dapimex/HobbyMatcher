package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.adapter.InviteActivityAdapter
import com.madness.hobbymatcher.fragment.*

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

    fun inject(addActivityFragment: AddActivityFragment)

    fun inject(inviteViewHolder: InviteActivityAdapter.InviteViewHolder)

    fun inject(homeFragment: HomeFragment)

    fun inject(loginActivity: LoginActivity)

    fun inject(registrationActivity: RegistrationActivity)

    fun inject(activityDetailFragment: ActivityDetailFragment)

    fun inject(invitationsFragment: InvitationsFragment)
}