package com.pablichj.templato.component.core.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.componentWithBackStackOnDeepLinkNavigateTo
import com.pablichj.templato.component.core.consumeBackPressedDefault
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.destroyChildComponent
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.core.stack.StackTransition
import com.pablichj.templato.component.core.util.EmptyNavigationComponentView

class TopBarComponent<T : TopBarStatePresenter>(
    val topBarStatePresenter: T,
    private val componentDelegate: TopBarComponentDelegate<T>,
    private val content: @Composable TopBarComponent<T>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit
) : Component(), ComponentWithBackStack {

    override val backStack = BackStack<Component>()
    override var childComponents: MutableList<Component> = mutableListOf()
    var activeComponent: MutableState<Component?> = mutableStateOf(null)
    var lastBackstackEvent: BackStack.Event<Component>? = null

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
            activeComponent.value = null
            backStack.clear()
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

    private fun onStackTopUpdate(topComponent: Component) {
        val selectedStackBarItem = componentDelegate.mapComponentToStackBarItem(topComponent)
        when (componentDelegate.showBackArrowStrategy) {
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

    private fun setTitleSectionForHomeClick(topBarItem: TopBarItem) {
        topBarStatePresenter.topBarState.value = TopBarState(
            title = topBarItem.label,
            onIconGlobalNavigationClick = { drawerNavigationProvider ->
                drawerNavigationProvider.open()
            },
            onTitleClick = { drawerNavigationProvider ->
                drawerNavigationProvider.open()
            }
        )
    }

    private fun setTitleSectionForBackClick(topBarItem: TopBarItem) {
        topBarStatePresenter.topBarState.value = TopBarState(
            title = topBarItem.label,
            onTitleClick = {
                handleBackPressed()
            },
            onIconGlobalNavigationClick = { drawerNavigationProvider ->
                drawerNavigationProvider.open()
            },
            backNavigationIcon = Icons.Filled.ArrowBack,
            onBackNavigationIconClick = {
                handleBackPressed()
            }
        )
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
        val activeComponentCopy = activeComponent.value
        if (activeComponentCopy != null) {
            content(modifier, activeComponentCopy)
        } else {
            EmptyNavigationComponentView(this@TopBarComponent)
        }
    }

}
