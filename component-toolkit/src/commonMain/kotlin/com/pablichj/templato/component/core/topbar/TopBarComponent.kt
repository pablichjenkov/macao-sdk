package com.pablichj.templato.component.core.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.findClosestDrawerNavigationComponent
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent

abstract class TopBarComponent(
    config: Config
) : StackComponent(config) {

    private lateinit var topBarState: TopBarState

    override fun onStackTopUpdate(topComponent: Component) {
        val selectedStackBarItem = getStackBarItemForComponent(topComponent)
        if (config.showBackArrowAlways) {
            setTitleSectionForBackClick(selectedStackBarItem)
        } else {
            if (backStack.size() > 1) {
                setTitleSectionForBackClick(selectedStackBarItem)
            } else {
                setTitleSectionForHomeClick(selectedStackBarItem)
            }
        }
    }

    protected abstract fun getStackBarItemForComponent(topComponent: Component): StackBarItem

    private fun setTitleSectionForHomeClick(stackBarItem: StackBarItem) {
        topBarState = TopBarState(
            onBackPress = { handleBackPressed() }
        ).apply {
            setTitleSectionState(
                TitleSectionStateHolder(
                    title = stackBarItem.label,
                    icon1 = resolveFirstIcon(),
                    onIcon1Click = {
                        findClosestDrawerNavigationComponent()?.open()
                    },
                    onTitleClick = {
                        findClosestDrawerNavigationComponent()?.open()
                    }
                )
            )
        }
    }

    private fun setTitleSectionForBackClick(stackBarItem: StackBarItem) {
        topBarState = TopBarState {
            handleBackPressed()
        }.apply {
            setTitleSectionState(
                TitleSectionStateHolder(
                    title = stackBarItem.label,
                    onTitleClick = {
                        handleBackPressed()
                    },
                    icon1 = resolveFirstIcon(),
                    onIcon1Click = {
                        findClosestDrawerNavigationComponent()?.open()
                    },
                    icon2 = Icons.Filled.ArrowBack,
                    onIcon2Click = {
                        handleBackPressed()
                    }
                )
            )
        }
    }

    private fun resolveFirstIcon(): ImageVector? {
        val canProvideGlobalNavigation = findClosestDrawerNavigationComponent() != null
        return if (canProvideGlobalNavigation) {
            Icons.Filled.Menu
        } else {
            null
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        Scaffold(
            modifier = modifier,
            topBar = { TopBar(topBarState) }
        ) { paddingValues ->
            DefaultStackComponentView(
                modifier = modifier.padding(paddingValues),
                onComponentSwipedOut = { topBarState.handleBackPress() }
            )
        }
    }

}
