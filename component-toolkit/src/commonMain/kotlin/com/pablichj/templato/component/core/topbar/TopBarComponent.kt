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
import com.pablichj.templato.component.core.stack.DefaultStackComponentView
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.core.stack.StackStyle

abstract class TopBarComponent(
    private val config: Config
) : StackComponent() {

    private val topBarStatePresenter: TopBarStatePresenter =
        DefaultTopBarStatePresenter(onHandleBackPress = ::handleBackPressed)

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
        topBarStatePresenter.topBarState.value = TopBarState(
            title = stackBarItem.label,
            icon1 = resolveFirstIcon(),
            onIcon1Click = {
                findClosestDrawerNavigationComponent()?.open()
            },
            onTitleClick = {
                findClosestDrawerNavigationComponent()?.open()
            }
        )
    }

    private fun setTitleSectionForBackClick(stackBarItem: StackBarItem) {
        topBarStatePresenter.topBarState.value = TopBarState(
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
            topBar = { TopBar(topBarStatePresenter) }
        ) { paddingValues ->
            DefaultStackComponentView(
                topBarComponent = this,
                modifier = modifier.padding(paddingValues),
                onComponentSwipedOut = { topBarStatePresenter.handleBackPress() }
            )
        }
    }

    class Config(
        val stackStyle: StackStyle = StackStyle(),
        val showBackArrowAlways: Boolean = true
    )

    companion object {
        val DefaultConfig = Config(
            StackStyle()
        )
    }

}
