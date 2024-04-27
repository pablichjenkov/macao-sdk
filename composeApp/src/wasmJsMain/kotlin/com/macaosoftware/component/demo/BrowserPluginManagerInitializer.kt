package com.macaosoftware.component.demo

import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.PluginManagerInitializer
import com.macaosoftware.util.MacaoResult

class BrowserPluginManagerInitializer : PluginManagerInitializer {

    override suspend fun initialize(): MacaoResult<PluginManager> {
        return MacaoResult.Success(PluginManager())
    }
}
