package com.macaosoftware.component.demo

import android.app.Activity
import com.macaosoftware.plugin.MacaoApplicationCallback
import com.macaosoftware.app.PluginFactory
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.PluginManagerInitializer
import com.macaosoftware.util.MacaoResult

class AndroidPluginManagerInitializer(private val activity: Activity) : PluginManagerInitializer {

    override suspend fun initialize(): MacaoResult<PluginManager> {

        // Example of a MacaoApplicationCallback Plugin, implemented in kotlin side
        // But perfectly an interface pointing to a specific plaform implementation could
        // have been used instead.
        val macaoAppCallbackPlugin = object : MacaoApplicationCallback {
            override fun onExit() {
                activity.finish()
            }
        }

        val macaoAppCallbackPluginFactory = object : PluginFactory<MacaoApplicationCallback> {
            override fun getPlugin(): MacaoApplicationCallback? {
                return macaoAppCallbackPlugin
            }
        }

        val pluginManager = PluginManager().apply {
            addFactory(macaoAppCallbackPluginFactory)
        }

        return MacaoResult.Success(pluginManager)
    }
}

