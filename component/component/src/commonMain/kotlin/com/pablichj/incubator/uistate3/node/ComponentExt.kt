package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.Composable
import com.pablichj.incubator.uistate3.node.backpress.BackPressHandler
import com.pablichj.incubator.uistate3.node.drawer.IDrawerNode
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkDestination

fun Component.findClosestIDrawerNode(): IDrawerNode? {
    var parentIterator: Component? = this.parentComponent
    while (parentIterator != null) {
        if (parentIterator is IDrawerNode) {
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
        treeContext.navigator.registerDestination(
            DeepLinkDestination(
                deepLinkMatcher = deepLinkMatcherCopy,
                component = this
            )
        )
    }
}

fun Component.dispatchAttachedToComponentTree(treeContext: TreeContext) {
    println("${clazz}::dispatchAttachedToComponentTree()")
    if (this is IParentComponent) {
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

