package com.macaosoftware.component.stack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component

@Composable
fun StackView(
    modifier: Modifier,
    backStack: BackStack<Component>,
    lastBackstackEvent: BackStack.Event<Component>?,
    onComponentSwipedOut: () -> Unit,
    stackViewAnimations: StackViewAnimations = StackViewAnimations.Default
) {
    val backStackSize = backStack.size()
    println("StackView, backStackSize = $backStackSize, lastBackstackEvent = $lastBackstackEvent")

    val animationType = when (lastBackstackEvent) {
        is BackStack.Event.Pop -> {
            if (backStack.size() > 0)
                AnimationType.Reverse
            else AnimationType.Exit
        }

        is BackStack.Event.PopEmptyStack -> {
            AnimationType.Exit
        }

        is BackStack.Event.Push -> {
            if (backStack.size() > 1)
                AnimationType.Direct
            else AnimationType.Enter
        }

        is BackStack.Event.PushEqualTop -> {
            AnimationType.Enter
        }

        null -> AnimationType.Enter
    }

    val topChildComponent = backStack.deque[backStackSize - 1]

    val prevComponent = if (backStackSize > 1) {
        backStack.deque[backStackSize - 2]
    } else null

    when (stackViewAnimations) {

        StackViewAnimations.NoAnimation -> {

        }

        StackViewAnimations.Default -> {
            StackViewWithDefaultAnimations(
                modifier = modifier,
                childComponent = topChildComponent,
                animationType = animationType
            )
        }

        StackViewAnimations.CustomPredictiveBack -> {
            StackViewWithCustomPredictiveBack(
                modifier = modifier,
                childComponent = topChildComponent,
                prevChildComponent = prevComponent,
                animationType = animationType,
                onComponentSwipedOut = onComponentSwipedOut
            )
        }
    }

}

enum class StackViewAnimations {
    NoAnimation,
    Default,
    CustomPredictiveBack,
}
