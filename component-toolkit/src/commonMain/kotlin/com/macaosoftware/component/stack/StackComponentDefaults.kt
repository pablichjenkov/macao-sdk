package com.macaosoftware.component.stack

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component

object StackComponentDefaults {

    fun createStackStatePresenter(): StackStatePresenterDefault {
        return StackStatePresenterDefault()
    }

    val DefaultStackComponentView: @Composable StackComponent<StackComponentViewModel>.(
        modifier: Modifier,
        activeChildComponent: Component
    ) -> Unit = { modifier, _ ->
        Box {
            StackView(
                modifier = modifier,
                backStack = backStack,
                lastBackstackEvent = lastBackstackEvent,
                onComponentSwipedOut = {},
                stackViewAnimations = StackViewAnimations.Default
            )
        }
    }

}
