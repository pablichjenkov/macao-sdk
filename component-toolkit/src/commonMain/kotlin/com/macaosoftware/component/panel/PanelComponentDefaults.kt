package com.macaosoftware.component.panel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object PanelComponentDefaults {

    fun createPanelStatePresenter(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        panelStyle: PanelStyle = PanelStyle(),
        panelHeaderState: PanelHeaderState = PanelHeaderStateDefault(
            title = "A Panel Header Title",
            description = "Some description or leave it blank",
            imageUri = "",
            style = panelStyle
        )
    ): PanelStatePresenterDefault {
        return PanelStatePresenterDefault(
            dispatcher,
            panelHeaderState = panelHeaderState,
            panelStyle = panelStyle
        )
    }

    val PanelComponentView: @Composable PanelComponent<PanelStatePresenterDefault>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit = { modifier, childComponent ->
        NavigationPanel(
            modifier = modifier,
            panelStatePresenter = panelStatePresenter
        ) {
            childComponent.Content(Modifier)
        }
    }

}
