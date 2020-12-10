package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.loginmanager.view.LoginActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [])
interface LoginActivitySubcomponent : AndroidInjector<LoginActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<LoginActivity>
}
