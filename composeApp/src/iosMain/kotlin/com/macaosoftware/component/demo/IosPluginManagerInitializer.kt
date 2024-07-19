package com.macaosoftware.component.demo

import com.macaosoftware.app.InitializationError
import com.macaosoftware.plugin.MacaoApplicationCallback
import com.macaosoftware.app.PluginFactory
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.PluginManagerInitializer
import com.macaosoftware.util.MacaoResult
import platform.posix.exit

class IosPluginManagerInitializer(iosBridge: IosBridge) : PluginManagerInitializer {

    override suspend fun initialize(): MacaoResult<PluginManager, InitializationError> {

        // Example of a MacaoApplicationCallback Plugin, implemented in kotlin side
        // But perfectly an interface pointing to a specific swift implementation could
        // have been passed in the iosBridge.
        val macaoAppCallbackPlugin = object : MacaoApplicationCallback {
            override fun onExit() {
                exit(0)
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
