package com.macaosoftware.plugin.app

import com.macaosoftware.component.core.Component
import com.macaosoftware.util.MacaoResult
import kotlin.native.ObjCName

@ObjCName("RootComponentProvider")
interface RootComponentInitializer {

    fun shouldShowLoader(): Boolean

    /**
     * Initialize the Root Component of the Macao Application.
     * */
    suspend fun initialize(pluginManager: PluginManager): MacaoResult<Component>
}
