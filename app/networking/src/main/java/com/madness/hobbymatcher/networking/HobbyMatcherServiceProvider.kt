package com.madness.hobbymatcher.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Deprecated("HobbyMatcherServiceProvider is deprecated", ReplaceWith("NetworkingModule"))
class HobbyMatcherServiceProvider {
    companion object {
        private val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()

        private val retrofit = Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build()

        private val services = HashMap<Class<*>, Any>()

        fun <S> obtain(service: Class<S>): S {
            var result = services[service]
            if (result == null) {
                result = retrofit.create(service)
                if (result != null) {
                    services[service] = result
                }
            }
            return service.cast(result)!!
        }
    }
}