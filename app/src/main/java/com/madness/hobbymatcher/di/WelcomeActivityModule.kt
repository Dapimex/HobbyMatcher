package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.loginmanager.view.WelcomeActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [WelcomeActivitySubcomponent::class])
abstract class WelcomeActivityModule {
    @Binds
    @IntoMap
    @ClassKey(WelcomeActivity::class)
    abstract fun bindYourAndroidInjectorFactory(factory: WelcomeActivitySubcomponent.Factory): AndroidInjector.Factory<*>
}