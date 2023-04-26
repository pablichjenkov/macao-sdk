package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import com.pablichj.templato.component.core.backpress.BackPressHandler
import com.pablichj.templato.component.core.drawer.DrawerNavigationComponent
import com.pablichj.templato.component.core.router.DeepLinkDestination

fun Component.findClosestDrawerNavigationComponent(): DrawerNavigationComponent? {
    var parentIterator: Component? = this.parentComponent
    while (parentIterator != null) {
        if (parentIterator is DrawerNavigationComponent) {
            return parentIterator
        }
        parentIterator = parentIterator.parentComponent
    }
    return null
}

fun Component.onAttachedToComponentTree(treeContext: TreeContext) {
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
}

fun Component.dispatchAttachedToComponentTree(treeContext: TreeContext) {
    println("${clazz}::dispatchAttachedToComponentTree()")
    if (this is ComponentWithChildren) {
        this.childComponents.forEach { it.dispatchAttachedToComponentTree(treeContext) }
    }
    this.onAttachedToComponentTree(treeContext)
}

@Composable
fun Component.consumeBackPressEvent() {
    BackPressHandler(
        component = this,
        onBackPressed = { this.backPressedCallbackDelegate.onBackPressed() }
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

