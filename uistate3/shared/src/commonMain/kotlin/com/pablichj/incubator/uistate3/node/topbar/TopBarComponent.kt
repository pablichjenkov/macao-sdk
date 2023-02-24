package com.pablichj.incubator.uistate3.node.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.backstack.BackStack
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult

open class TopBarComponent(
    val screenIcon: ImageVector? = null,
) : Component(), NavComponent {
    override val backStack = BackStack<Component>()
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val topBarState = TopBarState {
        handleBackPressed()
    }

    init {
        this@TopBarComponent.backStack.eventListener = { event ->
            processBackstackEvent(event)
        }
    }

    override fun start() {
        super.start()
        println("$clazz::start()")
        val childNodesCopy = childComponents
        if (activeComponent.value == null) {
            println("$clazz::start(). Pushing selectedIndex = $selectedIndex, children.size = ${childNodesCopy.size}")
            backStack.push(childNodesCopy[selectedIndex])
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
        if (getComponent().lifecycleState == LifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        val selectedNavItem = getNavItemFromNode(newTop)
        if (backStack.size() > 1) {
            setTitleSectionForBackClick(selectedNavItem)
        } else {
            setTitleSectionForHomeClick(selectedNavItem)
        }
    }

    override fun onDestroyChildComponent(component: Component) {
        if (component.lifecycleState == LifecycleState.Started) {
            component.stop()
            component.destroy()
        } else {
            component.destroy()
        }
    }

    // endregion

    private fun setTitleSectionForHomeClick(navItem: NavItem) {
        topBarState.setTitleSectionState(
            TitleSectionStateHolder(
                title = navItem.label,
                icon1 = resolveFirstIcon(),
                onIcon1Click = {
                    findClosestIDrawerNode()?.open()
                },
                onTitleClick = {
                    findClosestIDrawerNode()?.open()
                }
            )
        )
    }

    private fun setTitleSectionForBackClick(navItem: NavItem) {
        topBarState.setTitleSectionState(
            TitleSectionStateHolder(
                title = navItem.label,
                onTitleClick = {
                    handleBackPressed()
                },
                icon1 = resolveFirstIcon(),
                onIcon1Click = {
                    findClosestIDrawerNode()?.open()
                },
                icon2 = Icons.Filled.ArrowBack,
                onIcon2Click = {
                    handleBackPressed()
                }
            )
        )
    }

    private fun resolveFirstIcon(): ImageVector? {
        val canProvideGlobalNavigation = findClosestIDrawerNode() != null
        return if (canProvideGlobalNavigation) {
            Icons.Filled.Menu
        } else {
            screenIcon
        }
    }

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

    @Composable
    override fun Content(modifier: Modifier) {
        println("$clazz::Composing(), stack.size = ${backStack.size()}")
        TopBarRender(modifier, topBarState, activeComponent.value)
    }

}