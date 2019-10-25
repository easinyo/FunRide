package com.dubinostech.rideshareapp.model.loginModel

import com.dubinostech.rideshareapp.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
