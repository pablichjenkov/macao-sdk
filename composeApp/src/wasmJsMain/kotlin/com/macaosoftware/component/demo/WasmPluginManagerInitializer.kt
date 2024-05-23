package com.macaosoftware.component.demo

import com.macaosoftware.plugin.MacaoApplicationCallback
import com.macaosoftware.app.PluginFactory
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.PluginManagerInitializer
import com.macaosoftware.util.MacaoResult

class WasmPluginManagerInitializer : PluginManagerInitializer {

    override suspend fun initialize(): MacaoResult<PluginManager> {

        val macaoAppCallbackPlugin = object : MacaoApplicationCallback {
            override fun onExit() {
                // No way to automatically close a tab in the browser
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
