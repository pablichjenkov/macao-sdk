package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import com.pablichj.templato.component.core.backpress.BackPressHandler

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
