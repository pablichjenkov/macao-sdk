package com.macaosoftware.component.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.ComponentWithBackStack
import com.macaosoftware.component.core.Navigator
import com.macaosoftware.component.core.componentWithBackStackOnDeepLinkNavigateTo
import com.macaosoftware.component.core.consumeBackPressedDefault
import com.macaosoftware.component.core.deeplink.DeepLinkResult
import com.macaosoftware.component.core.destroyChildComponent
import com.macaosoftware.component.core.processBackstackEvent
import com.macaosoftware.component.stack.BackStack
import com.macaosoftware.component.stack.StackTransition
import com.macaosoftware.component.util.EmptyNavigationComponentView

class TopBarComponent<out VM : TopBarComponentViewModel>(
    viewModelFactory: TopBarComponentViewModelFactory<VM>,
    private val content: @Composable TopBarComponent<VM>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit
) : Component(), ComponentWithBackStack {

    val componentViewModel: VM = viewModelFactory.create(this)
    private val topBarStatePresenter = componentViewModel.topBarStatePresenter
    override val backStack = BackStack<Component>()
    override val navigator = Navigator(backStack)
    override var isFirstComponentInStackPreviousCache: Boolean = false
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
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
    }

    override fun onAttach() {
        println("${instanceId()}::onAttach()")
        componentViewModel.dispatchAttached()
    }

    override fun onStart() {
        println("${instanceId()}::onStart()")
        if (this.startedFromDeepLink) {
            return
        }
        if (activeComponent.value != null && !isFirstComponentInStackPreviousCache) {
            activeComponent.value?.dispatchStart()
        }
        componentViewModel.dispatchStart()
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
        activeComponent.value?.dispatchStop()
        lastBackstackEvent = null
        componentViewModel.dispatchStop()
    }

    override fun onDetach() {
        println("${instanceId()}::onDetach()")
        componentViewModel.dispatchDetach()
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

    private fun onStackTopUpdate(topComponent: Component) {
        val selectedStackBarItem = componentViewModel.mapComponentToStackBarItem(topComponent)
        when (componentViewModel.showBackArrowStrategy) {
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

    override fun onDetachChildComponent(component: Component) {
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
        return componentViewModel.onCheckChildForNextUriFragment(nextUriFragment)
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
