package com.pablichj.templato.component.core.navbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.NavigationComponentDefaultLifecycleHandler
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.childForNextUriFragment
import com.pablichj.templato.component.core.getNavItemFromComponent
import com.pablichj.templato.component.core.deepLinkNavigateTo
import com.pablichj.templato.component.core.destroyChildComponent
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.processBackstackTransition
import com.pablichj.templato.component.core.stack.AddAllPushStrategy
import com.pablichj.templato.component.core.stack.PushStrategy
import com.pablichj.templato.component.core.toNavItemDeco
import com.pablichj.templato.component.core.util.EmptyNavigationComponentView
import com.pablichj.templato.component.platform.CoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavBarComponent<T : NavBarStatePresenter>(
    val navBarStatePresenter: T,
    pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
    private val lifecycleHandler: NavigationComponent.LifecycleHandler = NavigationComponentDefaultLifecycleHandler(),
    dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    private var content: @Composable NavBarComponent<T>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit
) : Component(), NavigationComponent {

    override val backStack = createBackStack(pushStrategy)
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val coroutineScope = CoroutineScope(dispatchers.main)

    init {
        coroutineScope.launch {
            navBarStatePresenter.navItemClickFlow.collect { navItem ->
                backStack.push(navItem.component)
            }
        }
        backStack.eventListener = { event ->
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    override fun onStart() {
        lifecycleHandler.onStart(this)
    }

    override fun onStop() {
        lifecycleHandler.onStop(this)
    }

    override fun onDestroy() {
        lifecycleHandler.onDestroy(this)
    }

    override fun handleBackPressed() {
        println("${instanceId()}::handleBackPressed, backStack.size = ${backStack.size()}")
        if (backStack.size() > 1) {
            backStack.pop()
        } else {
            // We delegate the back event when the stack has 1 element and not 0. The reason is, if
            // we pop all the way to zero the stack empty view will be show for a fraction of
            // milliseconds and this creates an undesirable effect.
            delegateBackPressedToParent()
        }
    }

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        val navItemDecoNewList = navItems.map { it.toNavItemDeco() }
        navBarStatePresenter.setNavItemsDeco(navItemDecoNewList)
        navBarStatePresenter.selectNavItemDeco(navItemDecoNewList[selectedIndex])
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromComponent(newTop).let {
            println("${instanceId()}::updateSelectedNavItem(), selectedIndex = $it")
            navBarStatePresenter.selectNavItemDeco(it.toNavItemDeco())
            selectedIndex = childComponents.indexOf(newTop)
        }
    }

    override fun onDestroyChildComponent(component: Component) {
        destroyChildComponent()
    }

    // endregion

    // region: DeepLink

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        return (this as ComponentWithBackStack).deepLinkNavigateTo(matchingComponent)
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return (this as ComponentWithBackStack).childForNextUriFragment(nextUriFragment)
    }

    // endregion

    // region NavBar rendering

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
            EmptyNavigationComponentView(this@NavBarComponent)
        }
    }

    // endregion

    companion object {

        fun createDefaultNavBarStatePresenter(
            dispatcher: CoroutineDispatcher = Dispatchers.Main
        ): NavBarStatePresenterDefault {
            return NavBarStatePresenterDefault(
                dispatcher = dispatcher
            )
        }

        val DefaultNavBarComponentView: @Composable NavBarComponent<NavBarStatePresenterDefault>.(
            modifier: Modifier,
            childComponent: Component
        ) -> Unit = { modifier, childComponent ->
            NavigationBottom(
                modifier = modifier,
                navbarStatePresenter = navBarStatePresenter
            ) {
                childComponent.Content(Modifier)
            }
        }

    }

}