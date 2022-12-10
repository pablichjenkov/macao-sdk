package com.pablichj.encubator.node.panel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.encubator.node.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PanelNode(
    parentContext: NodeContext
) : BackStackNode<Node>(parentContext), NavigatorNode {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private var activeNode: Node? = null
    private var startingIndex = 0
    private var selectedIndex = 0
    private var navItems: MutableList<NavigatorNodeItem> = mutableListOf()
    private var childNodes: MutableList<Node> = mutableListOf()
    private val panelState = PanelState(emptyList())

    init {
        coroutineScope.launch {
            panelState.navItemClickFlow.collect { navItemClick ->
                pushNode(navItemClick.node)
            }
        }
    }

    override fun start() {
        super.start()
        val childNodesCopy = childNodes
        if (activeNode == null && childNodesCopy.isNotEmpty()) {
            pushNode(childNodesCopy[startingIndex]) // push() will call node.start() on success
        } else {
            activeNode?.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNode?.stop()
    }

    override fun onStackTopChanged(node: Node) {
        activeNode = node

        getNavItemFromNode(node)?.let {
            panelState.selectNavItem(it)
            selectedIndex = childNodes.indexOf(node)
        }

        println("PanelNode::onStackTopChanged = ${getNavItemFromNode(node)}")
    }

    private fun getNavItemFromNode(node: Node): NavigatorNodeItem? {
        return panelState.navItems.firstOrNull { it.node == node }
    }

    // region: NavigatorNode

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
                    activeNode = it
                }
            }
        }.toMutableList()

        panelState.navItems = navItems
        panelState.selectNavItem(navItems[startingIndex])

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
        panelState.navItems = navItems.toMutableList()
    }

    override fun removeNavItem(index: Int) {
        navItems.removeAt(index)
        childNodes.removeAt(index)
        // The call to toMutableList() should return a new stack variable that triggers
        // recomposition in navDrawerState.
        panelState.navItems = navItems.toMutableList()
    }

    override fun clearNavItems() {
        navItems.clear()
        childNodes.clear()
        stack.clear()
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """PanelNode.Content stack.size = ${stack.size}
                |lifecycleState = ${context.lifecycleState}
            """.trimMargin()
        )

        NavigationPanel(
            modifier = modifier,
            panelState = panelState
        ) {
            Box {
                if (screenUpdateCounter >= 0 && stack.size > 0) {
                    stack.peek().Content(Modifier)
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