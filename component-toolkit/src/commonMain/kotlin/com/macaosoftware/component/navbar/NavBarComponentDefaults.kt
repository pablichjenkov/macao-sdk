package com.macaosoftware.component.navbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object NavBarComponentDefaults {

    fun createNavBarStatePresenter(
        dispatcher: CoroutineDispatcher = Dispatchers.Main
    ): NavBarStatePresenterDefault {
        return NavBarStatePresenterDefault(
            dispatcher = dispatcher
        )
    }

    val NavBarComponentView: @Composable NavBarComponent<NavBarStatePresenterDefault>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit = { modifier, childComponent ->
        NavigationBottom(
            modifier = modifier,
            navbarStatePresenter = navBarStatePresenter
        ) {
            childComponent.Content(Modifier)
        }
    }

    fun createViewModel(): NavBarComponentDefaultViewModel {
        return NavBarComponentDefaultViewModel()
    }
}