package com.macaosoftware.app

import com.macaosoftware.component.viewmodel.ComponentViewModelFactory
import com.macaosoftware.plugin.MacaoPlugin

class PluginManager : PluginFactory {

    // todo: use a map with the factory developer id to cut to the chase
    private val pluginFactories = mutableListOf<PluginFactory>()

    fun addFactory(pluginFactory: PluginFactory) {
        pluginFactories.add(pluginFactory)
    }

    override fun <VM : ComponentViewModelFactory<*>> getViewModelFactoryOf(
        componentType: String
    ): VM? {
        pluginFactories.forEach { pluginFactory ->
            val viewModelFactory = pluginFactory.getViewModelFactoryOf<VM>(componentType)
            if (viewModelFactory != null) return viewModelFactory
        }
        return null
    }

    override fun <P : MacaoPlugin> getPluginImplementationOf(pluginType: P): P? {
        pluginFactories.forEach { pluginFactory ->
            val plugin = pluginFactory.getPluginImplementationOf<P>(pluginType)
            if (plugin != null) return plugin
        }
        return null
    }

}
