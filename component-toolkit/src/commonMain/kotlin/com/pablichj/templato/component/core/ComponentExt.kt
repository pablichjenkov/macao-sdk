package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.pablichj.templato.component.core.backpress.BackPressHandler
import com.pablichj.templato.component.core.drawer.DrawerNavigationComponent
import com.pablichj.templato.component.core.router.DefaultRouter
import com.pablichj.templato.component.core.router.Router

fun Component.getFirstParentMatching(
    condition: (Component) -> Boolean
): Component? {
    var parentIterator: Component? = this.parentComponent
    while (parentIterator != null) {
        val match = condition(parentIterator)
        if (match) {
            return parentIterator
        }
        parentIterator = parentIterator.parentComponent
    }
    return null
}

fun Component.getRouter(): Router? {
    val treeContext = getFirstParentMatching {
        it is ComponentTreeContext
    } as? ComponentTreeContext ?: return null

    return treeContext.router
}

fun Component.findClosestDrawerNavigationComponent(): DrawerNavigationComponent? {
    return getFirstParentMatching {
        it is DrawerNavigationComponent
    } as? DrawerNavigationComponent
}

/*internal fun Component.onAttachedToComponentTree(treeContext: TreeContext) {
    this.treeContext = treeContext

    // Register to handle deep links
    val deepLinkMatcherCopy = deepLinkMatcher
    if (deepLinkMatcherCopy != null) {
        treeContext.router.registerRoute(
            DeepLinkDestination(
                deepLinkMatcher = deepLinkMatcherCopy,
                component = this
            )
        )
    }
}*/

/*fun Component.dispatchAttachedToComponentTree(treeContext: TreeContext) {
    println("${clazz}::dispatchAttachedToComponentTree()")
    if (this is ComponentWithChildren) {
        this.childComponents.forEach { it.dispatchAttachedToComponentTree(treeContext) }
    }
    this.onAttachedToComponentTree(treeContext)
}*/

@Composable
fun Component.consumeBackPressEvent() {
    BackPressHandler(
        component = this,
        onBackPressed = { this.handleBackPressed() }
    )
}

/*
fun Component.jsonify(): JsonObject {
    val rootJsonObject = buildJsonObject {
        put("component_type", JsonPrimitive(clazz))

        if (this@jsonify is NavComponent) {
            val children = mutableListOf<JsonObject>()
            this@jsonify.childComponents.forEach {
                val childJson = it.jsonify()
                children.add(childJson)
            }
            putJsonArray("children") {
                for (child in children) add(child)
            }
        }

    }

    return rootJsonObject
}
*/
