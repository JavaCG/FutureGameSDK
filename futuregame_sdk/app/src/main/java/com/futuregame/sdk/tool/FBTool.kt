package com.futuregame.sdk.tool

import android.app.Activity
import android.content.Intent
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

object FBTool {

    private val callbackManager: CallbackManager by lazy { CallbackManager.Factory.create() }

    interface LoginListener {
        fun onSuccess(accessToken: String)
        fun onError(retMsg: String)
    }

    fun login(activity: Activity, loginListener: LoginListener) {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    loginResult
                        ?.let {
                            loginListener.onSuccess(it.accessToken.token)
                        }
                        ?: let {
                            loginListener.onError("accessToken null")
                        }
                }

                override fun onCancel() {
                    loginListener.onError("onCancel")
                }

                override fun onError(error: FacebookException?) {
                    if (error is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            logout()
                        }
                    }
                    loginListener.onError("onError ${error.toString()}")
                }
            })
        LoginManager.getInstance()
            .logInWithReadPermissions(activity, listOf("public_profile"))
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun logout() {
        LoginManager.getInstance().logOut()
    }
}