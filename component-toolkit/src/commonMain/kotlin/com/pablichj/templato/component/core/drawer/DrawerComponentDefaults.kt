package com.pablichj.templato.component.core.drawer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DrawerComponentDefaults {

    fun createDrawerStatePresenter(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        drawerStyle: DrawerStyle = DrawerStyle(),
        drawerHeaderState: DrawerHeaderState = DrawerHeaderDefaultState(
            title = "Header Title",
            description = "This is the default text. Provide your own text for your App",
            imageUri = "",
            style = drawerStyle
        )
    ): DrawerStatePresenterDefault {
        return DrawerStatePresenterDefault(
            dispatcher = dispatcher,
            drawerHeaderState = drawerHeaderState,
            drawerStyle = drawerStyle
        )
    }

    val DrawerComponentView: @Composable DrawerComponent<DrawerStatePresenterDefault>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit = { modifier, childComponent ->
        NavigationDrawer(
            modifier = modifier,
            statePresenter = drawerStatePresenter
        ) {
            childComponent.Content(Modifier)
        }
    }

}
