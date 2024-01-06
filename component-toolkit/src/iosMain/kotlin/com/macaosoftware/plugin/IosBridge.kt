package com.macaosoftware.plugin

import com.macaosoftware.plugin.account.AccountPlugin

@ObjCName(name = "IosBridge", exact = true)
class IosBridge(
    val platformLifecyclePlugin: PlatformLifecyclePlugin,
    val accountPlugin: AccountPlugin
)
