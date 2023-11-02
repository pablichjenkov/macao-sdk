package com.macaosoftware.component.drawer

import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.ComponentLifecycleState
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

class DrawerComponent<out VM : DrawerComponentViewModel>(
    viewModelFactory: DrawerComponentViewModelFactory<VM>,
    private var content: @Composable DrawerComponent<VM>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit
) : Component(), NavigationComponent, DrawerNavigationProvider {

    val componentViewModel: VM = viewModelFactory.create(this)
    private val drawerStatePresenter = componentViewModel.drawerStatePresenter
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
            drawerStatePresenter.navItemClickFlow.collect { navItemClick ->
                navigator.push(navItemClick.component)
            }
        }
        backStack.eventListener = { event ->
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    // region: ComponentLifecycle

    override fun onAttach() {
        componentViewModel.dispatchAttached()
    }

    override fun onStart() {
        with(componentViewModel) {
            navigationComponentLifecycleStart()
            dispatchStart()
        }
    }

    override fun onStop() {
        with(componentViewModel) {
            navigationComponentLifecycleStop()
            dispatchStop()
        }
    }

    override fun onDetach() {
        with(componentViewModel) {
            navigationComponentLifecycleDestroy()
            dispatchDetach()
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

    // region: IDrawerComponent

    override fun open() {
        println("${instanceId()}::open")
        drawerStatePresenter.setDrawerState(DrawerValue.Open)
    }

    override fun close() {
        println("${instanceId()}::close")
        drawerStatePresenter.setDrawerState(DrawerValue.Closed)
    }

    // endregion

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        val navItemDecoNewList = navItems.map { it.toDrawerNavItem() }
        drawerStatePresenter.setNavItemsDeco(navItemDecoNewList)
        drawerStatePresenter.selectNavItemDeco(navItemDecoNewList[selectedIndex])
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            navigator.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromComponent(newTop).let {
            println("${instanceId()}::updateSelectedNavItem(), selectedIndex = $it")
            drawerStatePresenter.selectNavItemDeco(it.toDrawerNavItem())
            selectedIndex = childComponents.indexOf(newTop)
        }
    }

    override fun onDetachChildComponent(component: Component) {
        destroyChildComponent()
    }

    // endregion

    // region: DeepLink

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return componentWithBackStackGetChildForNextUriFragment(nextUriFragment)
    }

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        return componentWithBackStackOnDeepLinkNavigateTo(matchingComponent)
    }

    // endregion

    // region Drawer rendering

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """${instanceId()}.Composing() stack.size = ${backStack.size()}
                |lifecycleState = ${lifecycleState}
            """
        )
        CompositionLocalProvider(
            LocalDrawerNavigationProvider provides this
        ) {
            val activeComponentCopy = activeComponent.value
            if (activeComponentCopy != null) {
                content(modifier, activeComponentCopy)
            } else {
                EmptyNavigationComponentView(this@DrawerComponent)
            }
        }
    }

    // endregion

}
