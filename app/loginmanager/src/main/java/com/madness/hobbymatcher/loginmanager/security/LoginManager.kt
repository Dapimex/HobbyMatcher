package com.madness.hobbymatcher.loginmanager.security

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.madness.hobbymatcher.networking.AuthService
import com.madness.hobbymatcher.networking.interceptors.CredentialsStore
import com.madness.hobbymatcher.networking.request.SignInRequest
import com.madness.hobbymatcher.networking.request.SignUpRequest
import com.madness.hobbymatcher.networking.response.AuthResponse
import com.madness.hobbymatcher.networking.response.ServiceError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

enum class LoginResult {
    SUCCESS, INVALID, NOT_EXIST, NO_INTERNET
}

class LoginManager
@Inject constructor(
    val context: Context,
    private val authService: AuthService,
    val credentials: CredentialsStore
) {

    val loggedIn get() = credentials.hasToken

    fun startSignIn(username: String, password: String): LiveData<LoginResult> {
        val success = MutableLiveData<LoginResult>()
        authService.signIn(SignInRequest(username, password))
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful) {
                        val signIn = response.body()
                        credentials.username = signIn?.username.orEmpty()
                        credentials.token = signIn?.accessToken.orEmpty()
                        success.postValue(LoginResult.SUCCESS)
                    } else {
                        val errorResponse = ServiceError.from(response.errorBody())
                        if (errorResponse.message == "wrong password") {
                            success.postValue(LoginResult.INVALID)
                        } else {
                            success.postValue(LoginResult.NOT_EXIST)
                        }
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    success.postValue(LoginResult.NO_INTERNET)
                }

            })
        return success
    }

    fun startSignUp(username: String, password: String): LiveData<LoginResult> {
        val success = MutableLiveData<LoginResult>()
        authService.signUp(SignUpRequest(username, password))
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful) {
                        val signUp = response.body()
                        credentials.username = signUp?.username.orEmpty()
                        credentials.token = signUp?.accessToken.orEmpty()
                        success.postValue(LoginResult.SUCCESS)
                    } else {
                        success.postValue(LoginResult.INVALID)
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    success.postValue(LoginResult.NO_INTERNET)
                }
            })
        return success
    }

    fun logout() {
        credentials.eraseToken()
        credentials.eraseUsername()
    }

}
