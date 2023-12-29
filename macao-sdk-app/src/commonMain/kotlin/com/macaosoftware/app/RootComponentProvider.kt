package com.macaosoftware.app

import com.macaosoftware.component.core.Component
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("RootComponentProvider")
interface RootComponentProvider {
    suspend fun provideRootComponent(
        pluginManager: PluginManager
    ): Component
}
