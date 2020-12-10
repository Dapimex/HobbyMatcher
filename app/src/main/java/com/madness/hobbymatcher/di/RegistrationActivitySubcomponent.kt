package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.loginmanager.view.RegistrationActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [])
interface RegistrationActivitySubcomponent : AndroidInjector<RegistrationActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<RegistrationActivity>
}
