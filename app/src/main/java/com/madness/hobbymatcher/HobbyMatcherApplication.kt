package com.madness.hobbymatcher

import android.app.Application
import com.madness.hobbymatcher.di.ApplicationComponent
import com.madness.hobbymatcher.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class HobbyMatcherApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this

        appComponent = DaggerApplicationComponent.create()
        appComponent.inject(this)
    }

    companion object {
        lateinit var APPLICATION: Application
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}