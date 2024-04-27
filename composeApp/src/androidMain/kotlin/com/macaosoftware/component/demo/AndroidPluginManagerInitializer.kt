package com.macaosoftware.component.demo

import android.content.Context
import com.macaosoftware.app.PluginManagerInitializer
import com.macaosoftware.app.PluginManager
import com.macaosoftware.util.MacaoResult

class AndroidPluginManagerInitializer(context: Context) : PluginManagerInitializer {

    override suspend fun initialize(): MacaoResult<PluginManager> {
        return MacaoResult.Success(PluginManager())
    }
}
