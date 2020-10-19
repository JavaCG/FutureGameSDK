package com.futuregame.sdk

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.futuregame.sdk.tool.FBTool
import com.futuregame.sdk.tool.GoogleTool
import com.futuregame.sdk.tool.LogTool
import com.futuregame.sdk.tool.MetaDataTool
import tw.com.tp6gl4cj86.android_http_tool.HttpTool
import tw.com.tp6gl4cj86.android_http_tool.Listener.HttpListenerAdapter
import tw.com.tp6gl4cj86.jjlog.JJLog
import java.lang.ref.WeakReference

object Singleton {

    private var activity: WeakReference<Activity?>? = null
    private val mFloatingView: FloatingView? by lazy { activity?.get()?.let { FloatingView(it) } }
    private var metaData: Bundle? = null

    private var loginListener: FutureSDK.LoginListener? = null

    @JvmStatic
    fun init(activity: Activity) {
        this.activity = WeakReference(activity)

        initMetaData(activity)

        JJLog.setIsLog(MetaDataTool.isLog())

        initHttpTool()
    }

    private fun initMetaData(activity: Activity) {
        val applicationInfo =
            activity.packageManager.getApplicationInfo(
                activity.packageName,
                PackageManager.GET_META_DATA
            )
        metaData = applicationInfo.metaData
    }

    private fun initHttpTool() {
        HttpTool.setIsApplicationJson(true)
        HttpTool.setStaticHttpListenerAdapter(object : HttpListenerAdapter() {
            override fun onSuccess(data: String?, log: String?) {
                super.onSuccess(data, log)
                LogTool.log(log)
            }

            override fun onFailure(statusCode: Int, body: String?, log: String?) {
                super.onFailure(statusCode, body, log)
                LogTool.log(log)
            }
        })
    }

    @JvmStatic
    fun getMetaData(): Bundle = metaData ?: Bundle()

    @JvmStatic
    fun showFloatingView() {
        mFloatingView?.showFloatingView()
    }

    @JvmStatic
    fun doLogin(activity: Activity, loginListener: FutureSDK.LoginListener) {
        // todo doLogin
        Singleton.loginListener = loginListener
        activity.startActivity(Intent(activity, FutureLoginActivity::class.java))
    }

    @JvmStatic
    fun logout() {
        FBTool.logout()
        GoogleTool.logout(activity?.get())
    }
}