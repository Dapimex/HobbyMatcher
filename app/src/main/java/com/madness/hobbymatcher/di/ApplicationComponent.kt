package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.MainActivity
import com.madness.hobbymatcher.networking.di.NetworkingModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkingModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}