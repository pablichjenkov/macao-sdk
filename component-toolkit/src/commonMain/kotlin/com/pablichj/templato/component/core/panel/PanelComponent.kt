package com.pablichj.templato.component.core.panel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.EmptyStackMessage
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.getChildForNextUriFragment
import com.pablichj.templato.component.core.getNavItemFromComponent
import com.pablichj.templato.component.core.onDeepLinkNavigateTo
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.processBackstackTransition
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.stack.AddAllPushStrategy
import com.pablichj.templato.component.core.stack.PushStrategy
import com.pablichj.templato.component.core.toNavItemDeco
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PanelComponent<T : PanelStatePresenter>(
    private val panelStatePresenter: T,
    config: Config = DefaultConfig,
    dispatchers: DispatchersProxy = DispatchersProxy.DefaultDispatchers,
    private val content: @Composable PanelComponent<T>.(
        modifier: Modifier,
        childComponent: Component
    ) -> Unit
) : Component(), NavigationComponent {
    override val backStack = createBackStack(config.pushStrategy)
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val coroutineScope = CoroutineScope(dispatchers.main)

    init {
        coroutineScope.launch {
            panelStatePresenter.navItemClickFlow.collect { navItemClick ->
                backStack.push(navItemClick.component)
            }
        }
        backStack.eventListener = { event ->
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    override fun onStart() {
        if (activeComponent.value == null) {
            println("${instanceId()}::start(). Pushing selectedIndex = $selectedIndex, children.size = ${childComponents.size}")
            if (childComponents.isNotEmpty()) {
                backStack.push(childComponents[selectedIndex])
            } else {
                println("${instanceId()}::start() with childComponents empty")
            }
        } else {
            println("${instanceId()}::start() with activeNodeState = ${activeComponent.value?.instanceId()}")
            activeComponent.value?.dispatchStart()
        }
    }

    override fun onStop() {
        println("${instanceId()}::stop()")
        activeComponent.value?.dispatchStop()
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

    // endregion

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        val navItemDecoNewList = navItems.map { it.toNavItemDeco() }
        panelStatePresenter.setNavItemsDeco(navItemDecoNewList)
        panelStatePresenter.selectNavItemDeco(navItemDecoNewList[selectedIndex])
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromComponent(newTop).let {
            println("${instanceId()}::updateSelectedNavItem(), selectedIndex = $it")
            panelStatePresenter.selectNavItemDeco(it.toNavItemDeco())
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

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        return (this as ComponentWithBackStack).onDeepLinkNavigateTo(matchingComponent)
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return (this as ComponentWithBackStack).getChildForNextUriFragment(nextUriFragment)
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
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                text = "${instanceId()} $EmptyStackMessage",
                textAlign = TextAlign.Center
            )
        }
    }

    // endregion

    class Config(
        val pushStrategy: PushStrategy<Component>,
        val panelHeaderStyle: PanelHeaderStyle,
        val diContainer: DiContainer
    )

    companion object {
        val DefaultConfig = Config(
            pushStrategy = AddAllPushStrategy(),
            panelHeaderStyle = PanelHeaderStyle(),
            diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
        )

        fun createDefaultPanelStatePresenter(
            dispatcher: CoroutineDispatcher = Dispatchers.Main,
            panelHeaderStyle: PanelHeaderStyle = PanelHeaderStyle()
        ): PanelStatePresenterDefault {
            return PanelStatePresenterDefault(
                dispatcher,
                PanelHeaderStateDefault(
                    title = "A Panel Header Title",
                    description = "Some description or leave it blank",
                    imageUri = "",
                    style = panelHeaderStyle
                )
            )
        }

        val DefaultPanelComponentView: @Composable PanelComponent<PanelStatePresenterDefault>.(
            modifier: Modifier,
            childComponent: Component
        ) -> Unit = { modifier, childComponent ->
            NavigationPanel(
                modifier = modifier,
                panelStatePresenter = panelStatePresenter
            ) {
                childComponent.Content(Modifier)
            }
        }
    }

}