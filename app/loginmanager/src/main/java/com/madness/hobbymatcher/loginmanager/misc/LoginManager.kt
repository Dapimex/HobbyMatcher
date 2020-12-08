package com.madness.hobbymatcher.loginmanager.misc

import com.madness.hobbymatcher.networking.AuthService
import com.madness.hobbymatcher.networking.HobbyMatcherServiceProvider

class LoginManager {
    private val authService = HobbyMatcherServiceProvider.obtain(AuthService::class.java)
}
