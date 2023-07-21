package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import com.pablichj.templato.component.core.backpress.BackPressHandler
import com.pablichj.templato.component.core.drawer.DrawerNavigationComponent
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
