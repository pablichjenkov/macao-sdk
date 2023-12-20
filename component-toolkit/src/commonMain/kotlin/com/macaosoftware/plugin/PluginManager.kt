package com.macaosoftware.plugin

//import com.macaosoftware.component.core.Component
//import com.macaosoftware.component.core.NavItem
//import com.macaosoftware.sdui.MacaoComponentFactory
//import com.macaosoftware.sdui.error.ComponentMissingImplementation
//import kotlinx.serialization.json.JsonObject

class PluginManager {
    /*
    // todo: use a map with the factory developer id to cut the chase
    private val macaoComponentFactories = mutableListOf<MacaoComponentFactory>()

    fun addFactory(macaoComponentFactory: MacaoComponentFactory) {
        macaoComponentFactories.add(macaoComponentFactory)
    }

    fun getNavItemOf(
        componentJson: JsonObject
    ): NavItem {
        lateinit var navItem: NavItem
        macaoComponentFactories.forEach { macaoComponentFactory ->
            navItem = macaoComponentFactory.getNavItemOf(componentJson)
            if (navItem.label != "Missing_Factory") return navItem
        }
        return navItem
    }

    fun getComponentInstanceOf(
        componentJson: JsonObject
    ): Component {
        lateinit var macaoComponent: Component
        macaoComponentFactories.forEach { macaoComponentFactory ->
            macaoComponent = macaoComponentFactory.getComponentInstanceOf(componentJson)
            if (macaoComponent !is ComponentMissingImplementation) return macaoComponent
        }
        return macaoComponent
    }
*/
}
