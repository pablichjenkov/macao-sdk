package com.macaosoftware.component.panel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.ComponentLifecycleState
import com.macaosoftware.component.core.ComponentWithBackStack
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.Navigator
import com.macaosoftware.component.core.componentWithBackStackGetChildForNextUriFragment
import com.macaosoftware.component.core.componentWithBackStackOnDeepLinkNavigateTo
import com.macaosoftware.component.core.consumeBackPressedDefault
import com.macaosoftware.component.core.deeplink.DeepLinkResult
import com.macaosoftware.component.core.destroyChildComponent
import com.macaosoftware.component.core.getNavItemFromComponent
import com.macaosoftware.component.core.processBackstackEvent
import com.macaosoftware.component.core.processBackstackTransition
import com.macaosoftware.component.core.push
import com.macaosoftware.component.core.resetNavigationComponent
import com.macaosoftware.component.util.EmptyNavigationComponentView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PanelComponent<out VM : PanelComponentViewModel>(
    viewModelFactory: PanelComponentViewModelFactory<VM>,
    private val content: @Composable PanelComponent<VM>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit
) : Component(), NavigationComponent {

    private val componentViewModel: VM = viewModelFactory.create(this)
    val panelStatePresenter = componentViewModel.panelStatePresenter
    override val backStack = createBackStack(componentViewModel.pushStrategy)
    override val navigator = Navigator(backStack)
    override var isFirstComponentInStackPreviousCache: Boolean = false
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val coroutineScope = CoroutineScope(componentViewModel.dispatchers.main)

    init {
        coroutineScope.launch {
            panelStatePresenter.navItemClickFlow.collect { navItemClick ->
                navigator.push(navItemClick.component)
            }
        }
        backStack.eventListener = { event ->
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
        componentViewModel.onCreate()
    }

    override fun onStart() {
        with(componentViewModel) {
            navigationComponentLifecycleStart()
            onStart()
        }
    }

    override fun onStop() {
        with(componentViewModel) {
            navigationComponentLifecycleStop()
            onStop()
        }
    }

    override fun onDestroy() {
        with(componentViewModel) {
            navigationComponentLifecycleDestroy()
            onDestroy()
        }
    }

    override fun handleBackPressed() {
        println("${instanceId()}::handleBackPressed, backStack.size = ${backStack.size()}")
        if (consumeBackPressedDefault().not()) {
            resetNavigationComponent()
            delegateBackPressedToParent()
        }
    }

    // endregion

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        val navItemDecoNewList = navItems.map { it.toPanelNavItem() }
        panelStatePresenter.setNavItemsDeco(navItemDecoNewList)
        panelStatePresenter.selectNavItemDeco(navItemDecoNewList[selectedIndex])
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            navigator.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromComponent(newTop).let {
            println("${instanceId()}::updateSelectedNavItem(), selectedIndex = $it")
            panelStatePresenter.selectNavItemDeco(it.toPanelNavItem())
            selectedIndex = childComponents.indexOf(newTop)
        }
    }

    override fun onDestroyChildComponent(component: Component) {
        destroyChildComponent()
    }

    // endregion

    // region: DeepLink

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        return (this as ComponentWithBackStack).componentWithBackStackOnDeepLinkNavigateTo(
            matchingComponent
        )
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return (this as ComponentWithBackStack).componentWithBackStackGetChildForNextUriFragment(
            nextUriFragment
        )
    }

    // endregion

    // region Panel rendering

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """${instanceId()}.Composing() stack.size = ${backStack.size()}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )
        val activeComponentCopy = activeComponent.value
        if (activeComponentCopy != null) {
            content(modifier, activeComponentCopy)
        } else {
            EmptyNavigationComponentView(this@PanelComponent)
        }
    }

    // endregion

}
