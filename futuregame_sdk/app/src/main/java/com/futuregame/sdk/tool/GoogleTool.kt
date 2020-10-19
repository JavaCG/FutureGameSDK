package com.futuregame.sdk.tool

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

object GoogleTool {

    private val RC_SIGN_IN = 5995

    private var loginListener: LoginListener? = null

    interface LoginListener {
        fun onSuccess(idToken: String)
        fun onError(retMsg: String)
    }

    private fun getGoogleSignInOptions(activity: Activity): GoogleSignInClient =
        GoogleSignIn.getClient(
            activity, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(MetaDataTool.googleClientId())
                .requestEmail()
                .build()
        )

    fun login(activity: Activity, loginListener: LoginListener) {
        GoogleTool.loginListener = loginListener
        activity.startActivityForResult(getGoogleSignInOptions(activity).signInIntent, RC_SIGN_IN)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                account?.idToken
                    ?.let {
                        loginListener?.onSuccess(it)
                    }
                    ?: let {
                        loginListener?.onError("idToken null")
                    }

            } catch (e: ApiException) {
                loginListener?.onError(e.statusCode.toString())
            }
        }
    }

    fun logout(activity: Activity?) {
        activity?.run {
            getGoogleSignInOptions(this).signOut()
        }
    }
}