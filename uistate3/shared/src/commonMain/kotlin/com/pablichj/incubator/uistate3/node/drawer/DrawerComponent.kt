package com.pablichj.incubator.uistate3.node.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.backstack.BackStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO: Ask for the Header Info to render the Drawer header
class DrawerComponent(

) : Component(), INavComponent, IDrawerNode {
    override val backStack = BackStack<Component>()
    override var navItems: MutableList<NodeItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private val navDrawerState = NavigationDrawerState(emptyList())
    private val nodeCoroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin

    init {
        nodeCoroutineScope.launch {
            navDrawerState.navItemClickFlow.collect { navItemClick ->
                backStack.push(navItemClick.component)
            }
        }
        backStack.eventListener = { event ->
            processBackstackEvent(event)
        }
    }

    override fun start() {
        super.start()
        val childNodesCopy = childComponents
        if (activeComponent.value == null) {
            if (childNodesCopy.size > selectedIndex) {
                println("$clazz::start(). Pushing selectedIndex = $selectedIndex")
                backStack.push(childNodesCopy[selectedIndex])
            } else {
                println("$clazz::start() childSize(${childNodesCopy.size}) < selectedIndex($selectedIndex) BAD!")
            }
        } else {
            println("$clazz::start() with activeNodeState = ${activeComponent.value?.clazz}")
            activeComponent.value?.start()
        }
    }

    override fun stop() {
        super.stop()
        println("$clazz::stop()")
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

    // region: IDrawerComponent

    override fun open() {
        println("DrawerNode::open")
        navDrawerState.setDrawerState(DrawerValue.Open)
    }

    override fun close() {
        println("DrawerNode::close")
        navDrawerState.setDrawerState(DrawerValue.Closed)
    }

    // endregion

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NodeItem>) {
        navDrawerState.navItems = navItems
        navDrawerState.selectNavItem(navItems[selectedIndex])
        if (getComponent().lifecycleState == LifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    override fun updateSelectedNavItem(newTop: Component) {
        getNavItemFromNode(newTop)?.let {
            println("DrawerNode::updateSelectedNavItem(), selectedIndex = $it")
            navDrawerState.selectNavItem(it)
            selectedIndex = childComponents.indexOf(newTop)
        }
    }

    private fun getNavItemFromNode(component: Component): NodeItem? {
        return navDrawerState.navItems.firstOrNull { it.component == component }
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

    // region: DeepLink

    override fun getDeepLinkNodes(): List<Component>  {
        return childComponents
    }

    override fun onDeepLinkMatchingNode(matchingComponent: Component) {
        println("DrawerNode.onDeepLinkMatchingNode() matchingNode = ${matchingComponent.subPath}")
        backStack.push(matchingComponent)
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """$clazz.Composing() stack.size = ${backStack.size()}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )

        NavigationDrawer(
            modifier = modifier,
            navDrawerState = navDrawerState
        ) {
            Box {
                val activeNodeUpdate = activeComponent.value
                if (activeNodeUpdate != null && backStack.size() > 0) {
                    activeNodeUpdate.Content(Modifier)
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = "Empty Stack, Please add some children",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}