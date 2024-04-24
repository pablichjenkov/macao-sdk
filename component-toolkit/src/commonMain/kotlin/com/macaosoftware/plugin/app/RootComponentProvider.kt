package com.macaosoftware.plugin.app

import com.macaosoftware.component.core.Component
import kotlin.native.ObjCName

@ObjCName("RootComponentProvider")
interface RootComponentProvider {

    /**
     *
     *
     * */
    suspend fun provideRootComponent(
        pluginManager: PluginManager
    ): Component
}
