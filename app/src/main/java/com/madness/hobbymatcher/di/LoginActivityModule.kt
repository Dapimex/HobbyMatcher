package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.loginmanager.view.LoginActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [LoginActivitySubcomponent::class])
abstract class LoginActivityModule {
    @Binds
    @IntoMap
    @ClassKey(LoginActivity::class)
    abstract fun bindYourAndroidInjectorFactory(factory: LoginActivitySubcomponent.Factory): AndroidInjector.Factory<*>
}
