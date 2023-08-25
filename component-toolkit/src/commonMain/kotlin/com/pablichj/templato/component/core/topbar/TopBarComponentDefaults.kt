package com.pablichj.templato.component.core.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.PredictiveBackstackView

object TopBarComponentDefaults {

    fun createTopBarStatePresenter(
        topBarStyle: TopBarStyle = TopBarStyle()
    ): TopBarStatePresenterDefault {
        return TopBarStatePresenterDefault(topBarStyle = topBarStyle)
    }

    val TopBarComponentView: @Composable TopBarComponent<TopBarStatePresenterDefault>.(
        modifier: Modifier,
        activeChildComponent: Component
    ) -> Unit = { modifier, activeChildComponent ->
        Scaffold(
            modifier = modifier,
            topBar = {
                TopBar(topBarStatePresenter)
            }
        ) { paddingValues ->
            PredictiveBackstackView(
                predictiveComponent = activeChildComponent,
                modifier = modifier.padding(paddingValues),
                backStack = backStack,
                lastBackstackEvent = lastBackstackEvent,
                onComponentSwipedOut = {
                    topBarStatePresenter.onBackPressEvent()
                }
            )
        }
    }
}
