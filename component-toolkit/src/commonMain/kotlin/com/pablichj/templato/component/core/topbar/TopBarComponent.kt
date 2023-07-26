package com.pablichj.templato.component.core.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.findClosestDrawerNavigationComponent
import com.pablichj.templato.component.core.navbar.NavBarStateDefault
import com.pablichj.templato.component.core.panel.NavigationPanel
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.panel.PanelStateDefault
import com.pablichj.templato.component.core.stack.DefaultStackComponentView
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.core.stack.StackStyle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class TopBarComponent<T : TopBarStatePresenter>(
    private val topBarStatePresenter: T,
    private val config: Config
) : StackComponent() {

    init {
        topBarStatePresenter.onBackPressEvent = {
            handleBackPressed()
        }
    }

    override fun onStackTopUpdate(topComponent: Component) {
        val selectedStackBarItem = getStackBarItemForComponent(topComponent)

        if (config.showBackArrowAlways) {
            setTitleSectionForBackClick(selectedStackBarItem)
            return
        }

        if (backStack.size() > 1) {
            setTitleSectionForBackClick(selectedStackBarItem)
        } else {
            setTitleSectionForHomeClick(selectedStackBarItem)
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

    var topBarContent: @Composable TopBarComponent<T>.(
        modifier: Modifier
    ) -> Unit = { modifier ->
        TopBar(this.topBarStatePresenter)
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """${instanceId()}.Composing() stack.size = ${backStack.size()}
                |lifecycleState = ${lifecycleState}
            """
        )
        Scaffold(
            modifier = modifier,
            topBar = { this.topBarContent(modifier) }
        ) { paddingValues ->
            DefaultStackComponentView(
                topBarComponent = this,
                modifier = modifier.padding(paddingValues),
                onComponentSwipedOut = {
                    topBarStatePresenter.onBackPressEvent()
                }
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

        fun createDefaultState(): DefaultTopBarStatePresenter {
            return DefaultTopBarStatePresenter()
        }

    }

}
