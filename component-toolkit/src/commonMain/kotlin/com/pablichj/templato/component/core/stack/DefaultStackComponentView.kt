package com.pablichj.templato.component.core.stack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.topbar.DefaultTopBarStatePresenter
import com.pablichj.templato.component.core.topbar.TopBarComponent

@Composable
fun DefaultStackComponentView(
    topBarComponent: TopBarComponent<*>,
    modifier: Modifier,
    onComponentSwipedOut: () -> Unit
) {
    println(
        """
          ${topBarComponent.instanceId()}::Composing(), backStack.size = ${topBarComponent.backStack.size()}
          lastBackstackEvent = ${topBarComponent.lastBackstackEvent}
        """
    )

    val animationType = when (topBarComponent.lastBackstackEvent) {
        is BackStack.Event.Pop -> {
            if (topBarComponent.backStack.size() > 0)
                AnimationType.Reverse
            else AnimationType.Exit
        }

        is BackStack.Event.PopEmptyStack -> {
            AnimationType.Enter
        }

        is BackStack.Event.Push -> {
            if (topBarComponent.backStack.size() > 1)
                AnimationType.Direct
            else AnimationType.Enter
        }

        is BackStack.Event.PushEqualTop -> {
            AnimationType.Enter
        }

        null -> AnimationType.Enter
    }

    val prevComponent = if (topBarComponent.backStack.size() > 1) {
        topBarComponent.backStack.deque[topBarComponent.backStack.size() - 2]
    } else {
        null
    }

    when (LocalBackPressedDispatcher.current.isSystemBackButtonEnabled()) {
        true -> {
            // Except Android, (and when the traditional 3 button navigation is enabled),
            // all the platforms will fall in to this case.
            StackCustomPredictiveBack(
                modifier = modifier,
                childComponent = topBarComponent.activeComponent.value,
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
                childComponent = topBarComponent.activeComponent.value,
                animationType = animationType
            )
        }
    }

}
