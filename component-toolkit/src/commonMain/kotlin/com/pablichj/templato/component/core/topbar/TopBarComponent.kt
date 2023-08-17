package com.pablichj.templato.component.core.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.drawer.DrawerNavigationProvider
import com.pablichj.templato.component.core.drawer.EmptyDrawerNavigationProvider
import com.pablichj.templato.component.core.drawer.LocalDrawerNavigationProvider
import com.pablichj.templato.component.core.stack.DefaultStackComponentView
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.core.stack.StackStyle
import com.pablichj.templato.component.platform.DispatchersProxy

abstract class TopBarComponent<T : TopBarStatePresenter>(
    private val topBarStatePresenter: T,
    private val config: Config = DefaultConfig,
    dispatchers: DispatchersProxy = DispatchersProxy.DefaultDispatchers,
) : StackComponent() {

    private var drawerNavigationProvider: DrawerNavigationProvider? = null

    init {
        topBarStatePresenter.onBackPressEvent = {
            handleBackPressed()
        }
    }

    override fun onStackTopUpdate(topComponent: Component) {
        val selectedStackBarItem = getStackBarItemForComponent(topComponent)
        when (config.showBackArrowStrategy) {
            ShowBackArrowStrategy.WhenParentCanHandleBack -> {
                // Assume parent can handle always, except web
                setTitleSectionForBackClick(selectedStackBarItem)
            }

            ShowBackArrowStrategy.Always -> {
                setTitleSectionForBackClick(selectedStackBarItem)
            }

            ShowBackArrowStrategy.WhenStackCountGreaterThanOne -> {
                if (backStack.size() > 1) {
                    setTitleSectionForBackClick(selectedStackBarItem)
                } else {
                    setTitleSectionForHomeClick(selectedStackBarItem)
                }
            }
        }
    }

    protected abstract fun getStackBarItemForComponent(topComponent: Component): StackBarItem

    private fun setTitleSectionForHomeClick(stackBarItem: StackBarItem) {
        topBarStatePresenter.topBarState.value = TopBarState(
            title = stackBarItem.label,
            icon1 = resolveGlobalNavigationIcon(),
            onIcon1Click = {
                drawerNavigationProvider?.open()
            },
            onTitleClick = {
                drawerNavigationProvider?.open()
            }
        )
    }

    private fun setTitleSectionForBackClick(stackBarItem: StackBarItem) {
        topBarStatePresenter.topBarState.value = TopBarState(
            title = stackBarItem.label,
            onTitleClick = {
                handleBackPressed()
            },
            icon1 = resolveGlobalNavigationIcon(),
            onIcon1Click = {
                drawerNavigationProvider?.open()
            },
            icon2 = Icons.Filled.ArrowBack,
            onIcon2Click = {
                handleBackPressed()
            }
        )
    }

    private fun resolveGlobalNavigationIcon(): ImageVector? {
        if (drawerNavigationProvider == null) return null

        return if (drawerNavigationProvider is EmptyDrawerNavigationProvider) {
            null
        } else {
            Icons.Filled.Menu
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
        drawerNavigationProvider = LocalDrawerNavigationProvider.current
        LaunchedEffect(key1 = this@TopBarComponent) {
            activeComponent.value?.let { onStackTopUpdate(it) }
        }
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
        val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always
    )

    companion object {
        val DefaultConfig = Config(
            StackStyle()
        )

        fun createDefaultTopBarStatePresenter(): TopBarStatePresenterDefault {
            return TopBarStatePresenterDefault()
        }

    }

}
