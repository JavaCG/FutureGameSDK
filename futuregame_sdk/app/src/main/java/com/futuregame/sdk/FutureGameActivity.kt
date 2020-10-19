package com.futuregame.sdk

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.facebook.rebound.*
import kotlinx.android.synthetic.main.activity_future_game.*


class FutureGameActivity : AppCompatActivity() {

    private val menuShowSpring by lazy {
        SpringSystem.create().createSpring().apply {
            springConfig = SpringConfig.fromOrigamiTensionAndFriction(40.0, 8.0)
            this.addListener(object : SimpleSpringListener() {
                override fun onSpringUpdate(spring: Spring) {
                    super.onSpringUpdate(spring)
                    val value = spring.currentValue
                    NestedScrollView.translationX = SpringUtil.mapValueFromRangeToRange(
                        value,
                        0.0,
                        1.0,
                        (-NestedScrollView.layoutParams.width).toDouble(),
                        0.0
                    ).toFloat()
                    Mask.alpha = value.toFloat()
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_future_game)

        initStyle()

        menuShowSpring.endValue = 1.0
    }

    override fun onDestroy() {
        menuShowSpring.removeAllListeners()
        super.onDestroy()
    }

    private fun initStyle() {
        overridePendingTransition(0, 0)

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

}