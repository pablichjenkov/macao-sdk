package com.macaosoftware.component.demo

import com.macaosoftware.plugin.account.AccountPlugin
import com.macaosoftware.plugin.account.AccountPluginEmpty

@ObjCName(name = "IosBridge", exact = true)
class IosBridge(
    val accountPlugin: AccountPlugin = AccountPluginEmpty()
)
