package com.futuregame.sdk.tool

import com.futuregame.sdk.Singleton
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
object MetaDataTool {

    fun isLog(): Boolean = Singleton.getMetaData().getBoolean(
        "Future.log",
        false
    )

    fun googleClientId(): String = Singleton.getMetaData().getString(
        "Future.googleClientId",
        "377579747571-84ai01matmtqj9r6v7km6n31v6tfn5oq.apps.googleusercontent.com"
    )

}