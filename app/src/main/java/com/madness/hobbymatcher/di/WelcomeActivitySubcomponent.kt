package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.loginmanager.view.WelcomeActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [])
interface WelcomeActivitySubcomponent : AndroidInjector<WelcomeActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<WelcomeActivity>
}