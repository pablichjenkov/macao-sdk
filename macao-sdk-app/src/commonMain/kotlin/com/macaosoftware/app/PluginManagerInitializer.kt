package com.macaosoftware.app

import com.macaosoftware.util.MacaoResult

interface PluginManagerInitializer {

    /**
     * Returns the PluginManager pass in each platform bridge.
     * The bridge will provide specific platfom actual implementations
     * of plugin interfaces.
     * */
    suspend fun initialize(): MacaoResult<PluginManager>
}
