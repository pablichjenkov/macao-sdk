package com.pablichj.templato.component.core.navbar

import androidx.compose.foundation.layout.fillMaxSize
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
import com.pablichj.templato.component.core.stack.AddAllPushStrategy
import com.pablichj.templato.component.core.stack.PushStrategy
import com.pablichj.templato.component.core.toNavItemDeco
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NavBarComponent(
    config: Config = DefaultConfig
) : Component(), NavigationComponent {
    override val backStack = createBackStack(config.pushStrategy)
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val coroutineScope = CoroutineScope(config.diContainer.dispatchers.main)
    val navBarState = NavBarStateDefault(config.diContainer.dispatchers.main, emptyList())

    init {
        coroutineScope.launch {
            navBarState.navItemClickFlow.collect { navItem ->
                backStack.push(navItem.component)
            }
        }
        backStack.eventListener = { event ->
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    override fun onStart() {
        if (activeComponent.value == null) {
            println("$clazz::start(). Pushing selectedIndex = $selectedIndex, children.size = ${childComponents.size}")
            if (childComponents.isNotEmpty()) {
                backStack.push(childComponents[selectedIndex])
            } else {
                println("$clazz::start() with childComponents empty")
            }
        } else {
            println("$clazz::start() with activeChild = ${activeComponent.value?.clazz}")
            activeComponent.value?.dispatchStart()
        }
    }

    override fun onStop() {
        println("$clazz::stop()")
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

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        val navItemDecoNewList = navItems.map { it.toNavItemDeco() }
        navBarState.setNavItemsDeco(navItemDecoNewList)
        navBarState.selectNavItemDeco(navItemDecoNewList[selectedIndex])
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromComponent(newTop).let {
            println("$clazz::updateSelectedNavItem(), selectedIndex = $it")
            navBarState.selectNavItemDeco(it.toNavItemDeco())
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

    // region NavBar rendering

    fun setNavBarComponentView(
        navBarComponentView: @Composable NavBarComponent.(
            modifier: Modifier,
            childComponent: Component
        ) -> Unit
    ) {
        this.navBarComponentView = navBarComponentView
    }

    private var navBarComponentView: @Composable NavBarComponent.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit = DefaultNavBarComponentView

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """$clazz.Composing() stack.size = ${backStack.size()}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )
        val activeComponentCopy = activeComponent.value
        if (activeComponentCopy != null) {
            navBarComponentView(modifier, activeComponentCopy)
        } else {
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                text = "$clazz Empty Stack, Please add some children",
                textAlign = TextAlign.Center
            )
        }
    }

    // endregion

    class Config(
        val pushStrategy: PushStrategy<Component>,
        val navBarStyle: NavBarStyle,
        val diContainer: DiContainer
    )

    companion object {
        val DefaultConfig = Config(
            pushStrategy = AddAllPushStrategy(),
            navBarStyle = NavBarStyle(),
            diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
        )

        val DefaultNavBarComponentView: @Composable NavBarComponent.(
            modifier: Modifier,
            childComponent: Component
        ) -> Unit = { modifier, childComponent ->
            NavigationBottom(
                modifier = modifier,
                navbarState = navBarState
            ) {
                childComponent.Content(Modifier)
            }
        }
    }

}