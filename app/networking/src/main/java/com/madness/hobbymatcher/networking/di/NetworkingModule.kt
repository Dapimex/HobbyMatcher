package com.madness.hobbymatcher.networking.di

import com.madness.hobbymatcher.networking.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkingModule {

    @Provides
    @Singleton
    fun providesActivityService(retrofit: Retrofit) : ActivityService {
        return retrofit.create(ActivityService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun providesInvitationService(retrofit: Retrofit) : InvitationService {
        return retrofit.create(InvitationService::class.java)
    }

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit) : UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}