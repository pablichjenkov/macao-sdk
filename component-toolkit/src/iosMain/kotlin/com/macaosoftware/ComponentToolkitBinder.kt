package com.macaosoftware

import com.macaosoftware.plugin.DefaultPlatformLifecyclePlugin
import com.macaosoftware.plugin.PlatformLifecyclePlugin
import com.macaosoftware.plugin.account.AccountPlugin
import com.macaosoftware.plugin.account.AccountPluginEmpty

@ObjCName(name = "ComponentToolkitBinder", exact = true)
class ComponentToolkitBinder {

    fun createDefaultPlatformLifecyclePlugin(): PlatformLifecyclePlugin {
        return DefaultPlatformLifecyclePlugin()
    }

    fun createAccountPluginEmpty(): AccountPlugin {
        return AccountPluginEmpty()
    }
}
