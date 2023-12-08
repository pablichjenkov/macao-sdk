package com.macaosoftware.component.core

import com.macaosoftware.component.core.deeplink.DeepLinkResult
import com.macaosoftware.component.stack.BackStack
import com.macaosoftware.component.stack.StackTransition

internal fun ComponentWithBackStack.processBackstackEvent(
    event: BackStack.Event<Component>
): StackTransition<Component> {
    return when (event) {
        is BackStack.Event.Push -> {
            println("${getComponent().instanceId()}::Event.Push")
            backstackRecords.isTopComponentStaled = false
            val stack = event.stack
            if (stack.size > 1) {
                val newTop = stack[stack.lastIndex]
                val oldTop = stack[stack.lastIndex - 1]
                transitionInOut(newTop, oldTop)
            } else {
                transitionIn(stack[0]) // There is only one item in the stack so lastIndex == 0
            }
        }

        is BackStack.Event.Pop -> {
            println("${getComponent().instanceId()}::Event.Pop")
            val stack = event.stack
            val oldTop = event.oldTop
            if (stack.isNotEmpty()) {
                val newTop = stack[stack.lastIndex]
                transitionInOut(newTop, oldTop)
            } else {
                transitionOut(oldTop) // There is no items in the stack
            }
        }

        is BackStack.Event.PushEqualTop -> {
            println(
                "${getComponent().instanceId()}::Event.PushEqualTop()," +
                        " backStack.size = ${backStack.size()}"
            )
            StackTransition.InvalidPushEqualTop<Component>()
        }

        is BackStack.Event.PopEmptyStack -> {
            println("${getComponent().instanceId()}::Event.PopEmptyStack(), backStack.size = 0")
            StackTransition.InvalidPopEmptyStack<Component>()
        }
    }
}

private fun ComponentWithBackStack.transitionIn(newTop: Component): StackTransition.In<Component> {
    println("${getComponent().instanceId()}::transitionIn(), newTop: ${newTop::class.simpleName}")
    newTop.setParent(getComponent())
    newTop.dispatchStart()
    return StackTransition.In(newTop)
}

private fun ComponentWithBackStack.transitionInOut(
    newTop: Component,
    oldTop: Component
): StackTransition.InOut<Component> {
    println(
        "${getComponent().instanceId()}::transitionInOut()," +
                " oldTop: ${oldTop::class.simpleName}, newTop: ${newTop::class.simpleName}"
    )
    // By convention always stop the previous top before starting the new one. TODO: Tests
    oldTop.dispatchStop()
    newTop.setParent(getComponent())
    newTop.dispatchStart()
    return StackTransition.InOut(newTop, oldTop)
}

private fun ComponentWithBackStack.transitionOut(oldTop: Component): StackTransition.Out<Component> {
    println("${getComponent().instanceId()}::transitionOut(), oldTop: ${oldTop::class.simpleName}")
    oldTop.dispatchStop()
    return StackTransition.Out(oldTop)
}

fun ComponentWithBackStack.componentWithBackStackOnDeepLinkNavigateTo(
    matchingComponent: Component
): DeepLinkResult {
    println("${getComponent().instanceId()}.onDeepLinkMatch() matchingNode = ${matchingComponent.instanceId()}")
    navigator.push(matchingComponent)
    return DeepLinkResult.Success(matchingComponent)
}

fun ComponentWithBackStack.componentWithBackStackGetChildForNextUriFragment(nextUriFragment: String): Component? {
    println("${getComponent().instanceId()}.getChildForNextUriFragment() nextUriFragment = $nextUriFragment")
    childComponents.forEach {
        println("${getComponent().instanceId()}::child.uriFragment = ${it.uriFragment}")
        val isUriFragmentMatch = it.uriFragment == nextUriFragment
        if (isUriFragmentMatch) {
            return it
        }
    }
    return null
}

fun ComponentWithBackStack.consumeBackPressedDefault(): Boolean {
    return if (backStack.size() > 1) {
        backStack.pop()
        true
    } else {
        backstackRecords.isTopComponentStaled = true
        false
    }
}
