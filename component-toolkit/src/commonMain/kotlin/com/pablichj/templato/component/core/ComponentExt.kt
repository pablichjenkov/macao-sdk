package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import com.pablichj.templato.component.core.backpress.BackPressHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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
fun Component.BackPressHandler() {
    BackPressHandler(
        component = this,
        onBackPressed = { handleBackPressed() }
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

suspend fun Component.repeatOnLifecycle(
    block: suspend CoroutineScope.() -> Unit
) {

    if (lifecycleState === ComponentLifecycleState.Destroyed) {
        return
    }

    // This scope is required to preserve context before we move to Dispatchers.Main
    coroutineScope {
        withContext(Dispatchers.Main.immediate) {
            // Check the current state of the lifecycle as the previous check is not guaranteed
            // to be done on the main thread.
            if (lifecycleState === ComponentLifecycleState.Destroyed) return@withContext

            // Instance of the running repeating coroutine
            var launchedJob: Job? = null

            try {
                lifecycleStateFlow.collect { componentLifecycleState ->
                    when (componentLifecycleState) {
                        ComponentLifecycleState.Created,
                        ComponentLifecycleState.Destroyed -> {
                            // no-op
                        }

                        ComponentLifecycleState.Started -> {
                            // Launch the repeating work preserving the calling context
                            launchedJob = this@coroutineScope.launch {
                                // coroutineScope ensures all child coroutines finish
                                coroutineScope {
                                    block()
                                }
                            }
                        }

                        ComponentLifecycleState.Stopped -> {
                            launchedJob?.cancel()
                            launchedJob = null
                        }
                    }

                }
            } finally {
                launchedJob?.cancel()
            }
        }
    }

}

@Composable
fun <T> Flow<T>.collectAsStateWithLifecycle(
    initialValue: T,
    component: Component,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> {
    return produceState(initialValue, this, component, context) {
        component.repeatOnLifecycle {
            if (context == EmptyCoroutineContext) {
                this@collectAsStateWithLifecycle.collect { this@produceState.value = it }
            } else withContext(context) {
                this@collectAsStateWithLifecycle.collect { this@produceState.value = it }
            }
        }
    }
}

@Composable
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
    component: Component,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> = collectAsStateWithLifecycle(
    initialValue = this.value,
    component = component,
    context = context
)
