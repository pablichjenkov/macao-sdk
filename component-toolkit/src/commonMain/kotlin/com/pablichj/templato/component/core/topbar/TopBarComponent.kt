package com.pablichj.templato.component.core.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.componentWithBackStackOnDeepLinkNavigateTo
import com.pablichj.templato.component.core.consumeBackPressedDefault
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.destroyChildComponent
import com.pablichj.templato.component.core.drawer.DrawerNavigationProvider
import com.pablichj.templato.component.core.drawer.EmptyDrawerNavigationProvider
import com.pablichj.templato.component.core.drawer.LocalDrawerNavigationProvider
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.core.stack.PredictiveBackstackView
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackTransition
import com.pablichj.templato.component.core.util.EmptyNavigationComponentView

class TopBarComponent<T : TopBarStatePresenter>(
    private val topBarStatePresenter: T,
    private val componentDelegate: TopBarComponentDelegate<T>,
    private val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always,
    private val content: @Composable TopBarComponent<T>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit
) : Component(), ComponentWithBackStack {

    override val backStack = BackStack<Component>()
    override var childComponents: MutableList<Component> = mutableListOf()
    var activeComponent: MutableState<Component?> = mutableStateOf(null)
    var lastBackstackEvent: BackStack.Event<Component>? = null
    private var drawerNavigationProvider: DrawerNavigationProvider? = null

    init {
        topBarStatePresenter.onBackPressEvent = {
            handleBackPressed()
        }
        this@TopBarComponent.backStack.eventListener = { event ->
            lastBackstackEvent = event
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
        with(componentDelegate) {
            create()
        }
    }

    override fun onStart() {
        println("${instanceId()}::onStart()")
        if (activeComponent.value != null) {
            activeComponent.value?.dispatchStart()
        }
        with(componentDelegate) {
            start()
        }
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
        activeComponent.value?.dispatchStop()
        lastBackstackEvent = null
        with(componentDelegate) {
            stop()
        }
    }

    override fun onDestroy() {
        println("${instanceId()}::onDestroy()")
    }

    override fun handleBackPressed() {
        println("${instanceId()}::handleBackPressed, backStack.size = ${backStack.size()}")
        if (consumeBackPressedDefault().not()) {
            delegateBackPressedToParent()
        }
    }

    // region: ComponentWithChildren

    override fun getComponent(): Component {
        return this
    }

    private fun processBackstackTransition(
        stackTransition: StackTransition<Component>
    ) {
        when (stackTransition) {
            is StackTransition.In -> {
                onStackTopUpdate(stackTransition.newTop)
                activeComponent.value = stackTransition.newTop
            }

            is StackTransition.InOut -> {
                onStackTopUpdate(stackTransition.newTop)
                activeComponent.value = stackTransition.newTop
            }

            is StackTransition.InvalidPushEqualTop -> {}
            is StackTransition.InvalidPopEmptyStack -> {
                activeComponent.value = null
            }

            is StackTransition.Out -> {
                activeComponent.value = null
            }
        }
    }

    fun onStackTopUpdate(topComponent: Component) {
        val selectedStackBarItem = getStackBarItemForComponent(topComponent)
        when (showBackArrowStrategy) {
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

    override fun onDestroyChildComponent(component: Component) {
        destroyChildComponent()
    }

    // endregion

    private fun getStackBarItemForComponent(topComponent: Component): StackBarItem {
        return componentDelegate.mapComponentToStackBarItem(topComponent)
    }

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

    override fun getChildForNextUriFragment(
        nextUriFragment: String
    ): Component? {
        return with(componentDelegate) {
            componentDelegateChildForNextUriFragment(nextUriFragment)
        }
    }

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        return componentWithBackStackOnDeepLinkNavigateTo(matchingComponent)
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """${instanceId()}.Composing() stack.size = ${backStack.size()}
                backStack.size = ${backStack.size()}
                lastBackstackEvent = ${lastBackstackEvent}          
                lifecycleState = ${lifecycleState}
            """
        )
        drawerNavigationProvider = LocalDrawerNavigationProvider.current
        val activeComponentCopy = activeComponent.value

        if (activeComponentCopy != null) {
            content(modifier, activeComponentCopy)
        } else {
            EmptyNavigationComponentView(this@TopBarComponent)
        }
    }

    companion object {

        fun createDefaultTopBarStatePresenter(
            topBarStyle: TopBarStyle = TopBarStyle()
        ): TopBarStatePresenterDefault {
            return TopBarStatePresenterDefault(topBarStyle = topBarStyle)
        }

        val DefaultTopBarComponentView: @Composable TopBarComponent<TopBarStatePresenterDefault>.(
            modifier: Modifier,
            activeChildComponent: Component
        ) -> Unit = { modifier, activeChildComponent ->
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopBar(this.topBarStatePresenter)
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

}
