package com.madness.hobbymatcher.loginmanager.security

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.madness.hobbymatcher.networking.AuthService
import com.madness.hobbymatcher.networking.HobbyMatcherServiceProvider
import com.madness.hobbymatcher.networking.response.SignIn
import com.madness.hobbymatcher.networking.response.SignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class LoginResult {
    SUCCESS, INVALID, NO_INTERNET
}

class LoginManager(context: Context) {
    private val authService = HobbyMatcherServiceProvider.obtain(AuthService::class.java)
    private val credentials = CredentialsStore(context)

    val loggedIn = credentials.hasToken

    fun startSignIn(username: String, password: String): LiveData<LoginResult> {
        val success = MutableLiveData<LoginResult>()
        authService.signIn(username, password).enqueue(object : Callback<SignIn> {
            override fun onResponse(call: Call<SignIn>, response: Response<SignIn>) {
                if (response.isSuccessful) {
                    val signIn = response.body()
                    credentials.token = signIn?.accessToken.orEmpty()
                    success.postValue(LoginResult.SUCCESS)
                } else {
                    success.postValue(LoginResult.INVALID)
                }
            }

            override fun onFailure(call: Call<SignIn>, t: Throwable) {
                success.postValue(LoginResult.NO_INTERNET)
            }

        })
        return success
    }

    fun startSignUp(username: String, password: String): LiveData<LoginResult> {
        val success = MutableLiveData<LoginResult>()
        authService.signUp(username, password).enqueue(object : Callback<SignUp> {
            override fun onResponse(call: Call<SignUp>, response: Response<SignUp>) {
                if (response.isSuccessful) {
                    val signUp = response.body()
                    credentials.token = signUp?.accessToken.orEmpty()
                    success.postValue(LoginResult.SUCCESS)
                } else {
                    success.postValue(LoginResult.INVALID)
                }
            }

            override fun onFailure(call: Call<SignUp>, t: Throwable) {
                success.postValue(LoginResult.NO_INTERNET)
            }
        })
        return success
    }

    fun logout() {
        credentials.eraseToken()
    }

}
