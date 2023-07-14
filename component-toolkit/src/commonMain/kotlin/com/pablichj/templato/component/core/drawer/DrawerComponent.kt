package com.pablichj.templato.component.core.drawer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.getChildForNextUriFragment
import com.pablichj.templato.component.core.getDeepLinkHandler
import com.pablichj.templato.component.core.getNavItemFromComponent
import com.pablichj.templato.component.core.onDeepLinkNavigation
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.processBackstackTransition
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkResult
import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.core.toNavItemDeco
import com.pablichj.templato.component.platform.DiContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DrawerComponent(
    private val config: Config,
    private val diContainer: DiContainer
) : Component(), NavigationComponent, DrawerNavigationComponent {
    override val backStack = BackStack<Component>()
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val coroutineScope = CoroutineScope(diContainer.dispatchers.main)
    val navigationDrawerState = NavigationDrawerState(
        coroutineScope,
        DrawerHeaderState(
            title = "A Drawer Header Title",
            description = "Some description or leave it blank",
            imageUri = "",
            style = config.drawerHeaderStyle
        ),
        emptyList()
    )

    init {
        coroutineScope.launch {
            navigationDrawerState.navItemClickFlow.collect { navItemClick ->
                backStack.push(navItemClick.component)
            }
        }
        backStack.eventListener = { event ->
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    // region: ComponentLifecycle

    override fun onStart() {
        if (activeComponent.value == null) {
            println("$clazz::onStart(). Pushing selectedIndex = $selectedIndex, children.size = ${childComponents.size}")
            if (childComponents.isNotEmpty()) {
                backStack.push(childComponents[selectedIndex])
            } else {
                println("$clazz::onStart() with childComponents empty")
            }
        } else {
            println("$clazz::onStart() with activeChild = ${activeComponent.value?.clazz}")
            activeComponent.value?.dispatchStart()
        }
    }

    override fun onStop() {
        println("$clazz::onStop()")
        activeComponent.value?.dispatchStop()
    }

    override fun handleBackPressed() {
        println("$clazz::handleBackPressed, backStack.size = ${backStack.size()}")
        if (backStack.size() > 1) {
            backStack.pop()
        } else {
            // We delegate the back event when the stack has 1 element and not 0. The reason is, if
            // we pop all the way to zero the stack empty view will be show for a fraction of
            // milliseconds and this creates an undesirable effect.
            delegateBackPressedToParent()
        }
    }

    // endregion

    // region: IDrawerComponent

    override fun open() {
        println("$clazz::open")
        navigationDrawerState.setDrawerState(DrawerValue.Open)
    }

    override fun close() {
        println("$clazz::close")
        navigationDrawerState.setDrawerState(DrawerValue.Closed)
    }

    // endregion

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        val navItemsDeco = navItems.map { it.toNavItemDeco() }
        navigationDrawerState.navItemsDeco = navItemsDeco
        navigationDrawerState.selectNavItemDeco(navItemsDeco[selectedIndex])
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromComponent(newTop).let {
            println("$clazz::updateSelectedNavItem(), selectedIndex = $it")
            navigationDrawerState.selectNavItemDeco(it.toNavItemDeco())
            selectedIndex = childComponents.indexOf(newTop)
        }
    }

    override fun onDestroyChildComponent(component: Component) {
        if (component.lifecycleState == ComponentLifecycleState.Started) {
            component.dispatchStop()
            component.dispatchDestroy()
        } else {
            component.dispatchDestroy()
        }
    }

    // endregion

    // region: DeepLink

    override fun onDeepLinkNavigation(matchingComponent: Component): DeepLinkResult {
        return (this as ComponentWithBackStack).onDeepLinkNavigation(matchingComponent)
    }

    override fun getDeepLinkHandler(): DeepLinkMatchData {
        return (this as ComponentWithBackStack).getDeepLinkHandler()
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return (this as ComponentWithBackStack).getChildForNextUriFragment(nextUriFragment)
    }

    // endregion

    // region Drawer rendering

    fun setDrawerComponentView(
        drawerComponentView: @Composable DrawerComponent.(
            modifier: Modifier,
            childComponent: Component
        ) -> Unit
    ) {
        this.drawerComponentView = drawerComponentView
    }

    private var drawerComponentView: @Composable DrawerComponent.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit = DefaultDrawerComponentView

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """$clazz.Composing() stack.size = ${backStack.size()}
                |lifecycleState = ${lifecycleState}
            """
        )
        //Box {
        val activeComponentCopy = activeComponent.value
        if (activeComponentCopy != null) {
            drawerComponentView(modifier, activeComponentCopy)
        } else {
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                //.align(Alignment.Center),
                text = "$clazz Empty Stack, Please add some children",
                textAlign = TextAlign.Center
            )
        }
        //}
    }

    // endregion

    class Config(
        var drawerHeaderStyle: DrawerHeaderStyle
    )

    companion object {
        val DefaultConfig = Config(
            DrawerHeaderStyle()
        )
        val DefaultDrawerComponentView: @Composable DrawerComponent.(
            modifier: Modifier,
            childComponent: Component
        ) -> Unit = { modifier, childComponent ->
            NavigationDrawer(
                modifier = modifier,
                navigationDrawerState = navigationDrawerState
            ) {
                childComponent.Content(Modifier)
            }
        }
    }

}
