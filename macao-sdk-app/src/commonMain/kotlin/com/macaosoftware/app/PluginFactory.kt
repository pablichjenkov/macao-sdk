package com.macaosoftware.app

import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.component.viewmodel.ComponentViewModelFactory
import com.macaosoftware.plugin.MacaoPlugin

interface PluginFactory {

    fun <VMF : ComponentViewModelFactory<*>> getViewModelFactoryOf(
        componentType: String
    ): VMF?

    fun <P : MacaoPlugin> getPluginImplementationOf(
        pluginType: P
    ): P?

    /*fun getNavItemOf(
        componentType: String
    ): NavItem*/
}
