package com.madness.hobbymatcher.di

import android.content.Context
import com.madness.hobbymatcher.HobbyMatcherApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideApplication() = HobbyMatcherApplication.APPLICATION

    @Provides
    fun provideContext(): Context = HobbyMatcherApplication.APPLICATION

}