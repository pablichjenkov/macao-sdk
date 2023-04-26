package com.pablichj.templato.component.core.navbar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.templato.component.core.router.DeepLinkResult
import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy
import com.pablichj.templato.component.core.*
import com.pablichj.templato.component.core.processBackstackEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class NavBarComponent(
    private val config: Config = DefaultConfig
) : Component(), NavigationComponent {
    final override val backStack = BackStack<Component>()
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val coroutineScope = CoroutineScope(config.diContainer.dispatchers.main)
    val navBarState = NavBarState(coroutineScope, emptyList())

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

    override fun start() {
        super.start()
        if (activeComponent.value == null) {
            println("$clazz::start(). Pushing selectedIndex = $selectedIndex, children.size = ${childComponents.size}")
            if (childComponents.isNotEmpty()) {
                backStack.push(childComponents[selectedIndex])
            } else {
                println("$clazz::start() with childComponents empty")
            }
        } else {
            println("$clazz::start() with activeNodeState = ${activeComponent.value?.clazz}")
            activeComponent.value?.start()
        }
    }

    override fun stop() {
        println("$clazz::stop()")
        super.stop()
        activeComponent.value?.stop()
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
        val navItemsDeco = navItems.map { it.toNavItemDeco() }
        navBarState.navItemsDeco = navItemsDeco
        navBarState.selectNavItemDeco(navItemsDeco[selectedIndex])
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    /**
     * TODO: Try to update the navitem instead, using a Backstack<NavItem>, sounds more efficient.
     * */
    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromComponent(newTop).let {
            println("$clazz::updateSelectedNavItem(), selectedIndex = $it")
            navBarState.selectNavItemDeco(it.toNavItemDeco())
            selectedIndex = childComponents.indexOf(newTop)
        }
    }

    override fun onDestroyChildComponent(component: Component) {
        if (component.lifecycleState == ComponentLifecycleState.Started) {
            component.stop()
            component.destroy()
        } else {
            component.destroy()
        }
    }

    // endregion

    // region: DeepLink

    override fun getDeepLinkSubscribedList(): List<Component> {
        return childComponents
    }

    override fun onDeepLinkNavigation(matchingComponent: Component): DeepLinkResult {
        println("$clazz.onDeepLinkMatch() matchingNode = ${matchingComponent.clazz}")
        backStack.push(matchingComponent)
        return DeepLinkResult.Success
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
        var navBarStyle: NavBarStyle,
        var diContainer: DiContainer
    )

    companion object {
        val DefaultConfig = Config(
            navBarStyle = NavBarStyle(),
            DiContainer(DispatchersProxy.DefaultDispatchers)
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