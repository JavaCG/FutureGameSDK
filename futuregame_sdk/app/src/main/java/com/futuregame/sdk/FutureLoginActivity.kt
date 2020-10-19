package com.futuregame.sdk

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.futuregame.sdk.api.API
import com.futuregame.sdk.tool.FBTool
import com.futuregame.sdk.tool.GoogleTool
import com.futuregame.sdk.tool.LogTool
import kotlinx.android.synthetic.main.activity_future_game.*


class FutureLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_future_login)
        overridePendingTransition(0, 0)

        initStyle()

        initFBLogin()
        initGoogleLogin()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun initStyle() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val flags =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = flags

        val decorView = window.decorView

        decorView.setOnSystemUiVisibilityChangeListener { visibility: Int ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                decorView.systemUiVisibility = flags
            }
        }

//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    private fun initFBLogin() {
        FBLogin.setOnClickListener {
            FBTool.login(this, object : FBTool.LoginListener {
                override fun onSuccess(accessToken: String) {
                    API.postFBLogin(this@FutureLoginActivity, accessToken)
                }

                override fun onError(retMsg: String) {
                    LogTool.log("Facebook login : failed = $retMsg")
                }
            })
        }
    }

    private fun initGoogleLogin() {
        GoogleLogin.setOnClickListener {
            GoogleTool.login(this, object : GoogleTool.LoginListener {
                override fun onSuccess(idToken: String) {
                    API.postGoogleLogin(this@FutureLoginActivity, idToken)
                }

                override fun onError(retMsg: String) {
                    LogTool.log("Google login : failed code = $retMsg")
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FBTool.onActivityResult(requestCode, resultCode, data)
        GoogleTool.onActivityResult(requestCode, resultCode, data)
    }

}