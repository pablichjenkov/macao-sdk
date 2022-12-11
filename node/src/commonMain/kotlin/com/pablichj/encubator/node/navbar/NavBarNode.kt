package com.pablichj.encubator.node.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.encubator.node.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavBarNode(
    parentContext: NodeContext
) : BackStackNode<Node>(parentContext), NavigatorNode {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private var activeNodeState: MutableState<Node?> = mutableStateOf(null)
    private var startingIndex = 0
    private var selectedIndex = 0
    private var navItems: MutableList<NavigatorNodeItem> = mutableListOf()
    private var childNodes: MutableList<Node> = mutableListOf()
    private val navBarState = NavBarState(emptyList())

    init {
        coroutineScope.launch {
            navBarState.navItemClickFlow.collect { navItemClick ->
                pushNode(navItemClick.node)
            }
        }
    }

    override fun start() {
        super.start()
        val childNodesCopy = childNodes
        if (activeNodeState.value == null && childNodesCopy.isNotEmpty()) {
            pushNode(childNodesCopy[startingIndex]) // push() will call node.start() on success
        } else {
            activeNodeState.value?.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNodeState.value?.stop()
    }

    override fun onStackPushSuccess(oldTop: Node?, newTop: Node) {
        println("NavBarNode::onStackPop(), oldTop: ${oldTop?.javaClass?.simpleName}," +
                " newTop: ${newTop.javaClass.simpleName}")

        activeNodeState.value = newTop
        newTop.start()
        oldTop?.stop()

        updateSelectedNavItem(newTop)
    }

    override fun onStackPopSuccess(oldTop: Node, newTop: Node?) {
        println("NavBarNode::onStackPop(), oldTop: ${oldTop.javaClass.simpleName}," +
                " newTop: ${newTop?.javaClass?.simpleName}")

        activeNodeState.value = newTop
        newTop?.start()
        oldTop.stop()

        newTop?.let { updateSelectedNavItem(it) }
    }

    private fun updateSelectedNavItem(newTop: Node) {
        getNavItemFromNode(newTop)?.let {
            println("NavBarNode::updateSelectedNavItem(), selectedIndex = $it")
            navBarState.selectNavItem(it)
            selectedIndex = childNodes.indexOf(newTop)
        }
    }

    private fun getNavItemFromNode(node: Node): NavigatorNodeItem? {
        return navBarState.navItems.firstOrNull { it.node == node }
    }

    // endregion

    // region: NavigatorItems

    override fun getNode(): Node {
        return this
    }

    override fun getSelectedNavItemIndex(): Int {
        return selectedIndex
    }

    override fun setNavItems(
        navItemsList: MutableList<NavigatorNodeItem>,
        startingIndex: Int
    ) {
        this.startingIndex = startingIndex
        this.selectedIndex = startingIndex

        navItems = navItemsList.map { it }.toMutableList()

        childNodes = navItems.map { navItem ->
            navItem.node.also {
                it.context.updateParent(context)
                if (it.context.lifecycleState == LifecycleState.Started) {
                    activeNodeState.value = it
                }
            }
        }.toMutableList()

        navBarState.navItems = navItems
        navBarState.selectNavItem(navItems[startingIndex])

        if (context.lifecycleState == LifecycleState.Started) {
            pushNode(childNodes[startingIndex])
        }
    }

    override fun getNavItems(): MutableList<NavigatorNodeItem> {
        return navItems
    }

    override fun addNavItem(navItem: NavigatorNodeItem, index: Int) {
        navItems.add(index, navItem)
        childNodes.add(index, navItem.node)
        // The call to toMutableList() should return a new stack variable that triggers
        // recomposition in navDrawerState.
        navBarState.navItems = navItems.toMutableList()
    }

    override fun removeNavItem(index: Int) {
        navItems.removeAt(index)
        childNodes.removeAt(index)
        // The call to toMutableList() should return a new stack variable that triggers
        // recomposition in navDrawerState.
        navBarState.navItems = navItems.toMutableList()
    }

    override fun clearNavItems() {
        println("Navbar.clearNavItems")
        navItems.clear()
        childNodes.clear()
        stack.clear()
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """NavBarNode.Composing() stack.size = ${stack.size}
                |lifecycleState = ${context.lifecycleState}
            """.trimMargin()
        )

        NavigationBottom(
            modifier = modifier,
            navbarState = navBarState
        ) {
            Box {
                val activeNodeUpdate = activeNodeState.value
                if (activeNodeUpdate != null && stack.size > 0) {
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