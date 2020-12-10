package com.madness.hobbymatcher.di

import com.madness.hobbymatcher.loginmanager.view.RegistrationActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [RegistrationActivitySubcomponent::class])
abstract class RegistrationActivityModule {
    @Binds
    @IntoMap
    @ClassKey(RegistrationActivity::class)
    abstract fun bindYourAndroidInjectorFactory(factory: RegistrationActivitySubcomponent.Factory): AndroidInjector.Factory<*>
}
