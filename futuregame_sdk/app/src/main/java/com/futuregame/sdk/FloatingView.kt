package com.futuregame.sdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.view.*
import android.view.WindowManager.LayoutParams.*

class FloatingView(activity: Activity) {

    private val windowManager by lazy { activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    private val windowView by lazy {
        LayoutInflater.from(activity).inflate(R.layout.fg_floating_view, null).apply {
            var lastTouchX = 0f
            var lastTouchY = 0f

            val mGestureDetector =
                GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDown(event: MotionEvent?): Boolean {
                        event?.run {
                            lastTouchX = rawX
                            lastTouchY = rawY
                        }
                        return super.onDown(event)
                    }

                    override fun onScroll(
                        event1: MotionEvent?,
                        event2: MotionEvent?,
                        distanceX: Float,
                        distanceY: Float
                    ): Boolean {
                        event2?.run {
                            val currentX = rawX
                            val currentY = rawY
                            val dx = currentX - lastTouchX
                            val dy = currentY - lastTouchY

//                            mLayoutParams.x += dx.toInt()
                            mLayoutParams.y += dy.toInt()
                            windowManager.updateViewLayout(this@apply, mLayoutParams)

                            lastTouchX = currentX
                            lastTouchY = currentY
                        }
                        return super.onScroll(event1, event2, distanceX, distanceY)
                    }

                    override fun onSingleTapUp(e: MotionEvent?): Boolean {
                        activity.startActivity(Intent(activity, FutureGameActivity::class.java))
                        return super.onSingleTapUp(e)
                    }
                })

            setOnTouchListener { _, motionEvent -> mGestureDetector.onTouchEvent(motionEvent) }
        }
    }

    private val mLayoutParams by lazy {
        WindowManager.LayoutParams().apply {
            width = WRAP_CONTENT
            height = WRAP_CONTENT
            type = TYPE_APPLICATION
            flags =
                FLAG_NOT_TOUCH_MODAL or FLAG_NOT_FOCUSABLE or FLAG_LAYOUT_NO_LIMITS or FLAG_FULLSCREEN or FLAG_TRANSLUCENT_NAVIGATION
            format = PixelFormat.TRANSLUCENT
            softInputMode = SOFT_INPUT_ADJUST_PAN
            gravity = Gravity.START or Gravity.TOP
            x = 0
            y = 0
        }
    }

    init {

    }

    fun showFloatingView() {
        windowView.parent ?: let {
            windowManager.addView(windowView, mLayoutParams)
        }
    }
}