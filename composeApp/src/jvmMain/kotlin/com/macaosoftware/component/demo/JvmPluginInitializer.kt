package com.macaosoftware.component.demo

import com.macaosoftware.plugin.app.Initializer
import com.macaosoftware.plugin.app.PluginManager
import com.macaosoftware.util.MacaoResult
import kotlinx.coroutines.delay

class JvmPluginInitializer : Initializer<PluginManager> {

    override fun shouldShowLoader(): Boolean {
        return true
    }

    override suspend fun initialize(): MacaoResult<PluginManager> {

        // TODO: Remove this, just for testing
        delay(1000)
        // Should initialize the minimum necessary dependencies to run the App
        // 1- Koin-Inject or Koin or PluginManager(manual DI)
        // 2- Database Migration
        return MacaoResult.Success(PluginManager())
    }
}
