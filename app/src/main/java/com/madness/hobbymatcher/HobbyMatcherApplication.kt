package com.madness.hobbymatcher

import android.app.Application
import com.madness.hobbymatcher.di.ApplicationComponent
import com.madness.hobbymatcher.di.DaggerApplicationComponent

class HobbyMatcherApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this

        appComponent = DaggerApplicationComponent.create()
    }

    companion object {
        lateinit var APPLICATION: Application
    }
}