package com.macaosoftware.component.demo

import com.macaosoftware.plugin.app.PluginManager
import com.macaosoftware.plugin.app.PluginManagerInitializer
import com.macaosoftware.util.MacaoResult

class JvmPluginManagerInitializer : PluginManagerInitializer {

    override suspend fun initialize(): MacaoResult<PluginManager> {

        // Should initialize the minimum necessary dependencies to run the App
        // Koin-Inject or Koin or PluginManager(for manual DI)
        return MacaoResult.Success(PluginManager())
    }
}
