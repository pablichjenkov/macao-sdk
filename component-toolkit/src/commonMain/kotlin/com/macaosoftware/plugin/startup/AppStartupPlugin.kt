package com.macaosoftware.plugin.startup

import kotlin.native.ObjCName

@ObjCName(name = "AppStartupPlugin", exact = true)
interface AppStartupPlugin {

    fun start()
}