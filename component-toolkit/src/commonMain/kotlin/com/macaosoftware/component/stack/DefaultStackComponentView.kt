package com.macaosoftware.component.stack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.util.LocalBackPressedDispatcher

@Composable
fun PredictiveBackstackView(
    modifier: Modifier,
    predictiveComponent: Component,
    backStack: BackStack<Component>,
    lastBackstackEvent: BackStack.Event<Component>?,
    onComponentSwipedOut: () -> Unit
) {

    println("PredictiveBackstackView, backStackSize = ${backStack.size()}, lastBackstackEvent = ${lastBackstackEvent}")

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

    val prevComponent = if (backStack.size() > 1) {
        backStack.deque[backStack.size() - 2]
    } else {
        null
    }

    when (LocalBackPressedDispatcher.current.isSystemBackButtonEnabled()) {
        true -> {
            // Except Android, (and when the traditional 3 button navigation is enabled),
            // all the platforms will fall in to this case.
            StackCustomPredictiveBack(
                modifier = modifier,
                childComponent = predictiveComponent,
                prevChildComponent = prevComponent,
                animationType = animationType,
                onComponentSwipedOut = onComponentSwipedOut
            )
        }

        false -> {
            // In Android, ff the traditional back button is enabled then we use our custom
            // predictive back. Otherwise, if the user has gesture navigation enabled, we let the
            // system gesture takes care of the App.
            StackSystemPredictiveBack(
                modifier = modifier,
                childComponent = predictiveComponent,
                animationType = animationType
            )
        }
    }

}
