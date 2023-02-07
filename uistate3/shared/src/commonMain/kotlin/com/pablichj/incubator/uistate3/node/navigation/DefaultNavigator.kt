package com.pablichj.incubator.uistate3.node.navigation

import com.pablichj.incubator.uistate3.node.Component

object DefaultNavigator : Navigator{//todo: Make it a class and put the instance in the Local provider of the root component

    private val destinationRegistry = mutableListOf<Destination>()

    override fun registerDestination(destination: Destination) {
        destinationRegistry.add(destination)
    }

    override fun unregisterDestination(destination: Destination) {
        destinationRegistry.remove(destination)
    }

    override fun handleDeepLink(destination: String): DeepLinkResult {
        val componentDestination = destinationRegistry.firstOrNull { it.destination == destination }
            ?: return DeepLinkResult.Error(
                """
                Destination registry has no Component with destination = $destination
            """
            )

        val path = buildComponentPathFromRoot(componentDestination.component)

        val rootComponent = path.removeFirstOrNull() ?: return DeepLinkResult.Error(
            """
                Component with destination = $destination, is not attached to any parent Component
            """
        )

        println("path.size = ${path.size}")
        path.forEach { println("Component -> ${it.clazz}") }

        // The component with parentComponent == null is the root component
        return rootComponent.navigateToDeepLink(path)
    }

    private fun buildComponentPathFromRoot(component: Component): ArrayDeque<Component> {
        val path = ArrayDeque<Component>()
        var componentIterator: Component? = component
        while (componentIterator != null) {
            /*if (parentIterator is IRootComponent) {
                return path
            }*/
            path.add(0, componentIterator)
            componentIterator = componentIterator.parentComponent
        }
        return path
    }

}