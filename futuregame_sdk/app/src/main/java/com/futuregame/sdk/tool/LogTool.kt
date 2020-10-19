package com.futuregame.sdk.tool

import tw.com.tp6gl4cj86.jjlog.JJLog

object LogTool {
    fun log(log: String?) {
        JJLog.e("FutureSDK", log)
    }
}