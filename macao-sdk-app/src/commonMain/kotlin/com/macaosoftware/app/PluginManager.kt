package com.macaosoftware.app

import com.macaosoftware.plugin.MacaoPlugin

class PluginManager {

    // todo: Add the possibility to fecth a plugin by a specific id, not just the type
    // It will allow to combine several plugins instances of the same type.
    private val pluginFactories = mutableListOf<PluginFactory<out MacaoPlugin>>()

    fun addFactory(pluginFactory: PluginFactory<out MacaoPlugin>) {
        pluginFactories.add(pluginFactory)
    }

    /**
     * Returns the result of the first factory of the specified plugin type that
     * returns a non-null instance of the required plugin type.
     * */
    fun <T : MacaoPlugin> getPlugin(): T? {
        pluginFactories.forEach { pluginFactory ->
            val plugin = pluginFactory.getPlugin() as? T
            if (plugin != null) return plugin
        }
        return null
    }

}
