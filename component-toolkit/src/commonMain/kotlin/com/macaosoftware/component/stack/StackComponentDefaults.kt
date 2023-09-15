package com.macaosoftware.component.stack

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.drawer.DrawerHeaderDefaultState
import com.macaosoftware.component.drawer.DrawerHeaderState
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.drawer.DrawerStyle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object StackComponentDefaults {

    fun createStackStatePresenter(): StackStatePresenterDefault {
        return StackStatePresenterDefault()
    }

    val DefaultStackComponentView: @Composable StackComponent<StackStatePresenterDefault>.(
        modifier: Modifier,
        activeChildComponent: Component
    ) -> Unit = { modifier, activeChildComponent ->
        Box {
            PredictiveBackstackView(
                modifier = modifier,
                predictiveComponent = activeChildComponent,
                backStack = backStack,
                lastBackstackEvent = lastBackstackEvent,
                onComponentSwipedOut = {}
            )
        }
    }

}