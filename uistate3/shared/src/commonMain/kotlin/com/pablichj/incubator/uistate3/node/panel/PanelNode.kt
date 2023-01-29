package com.pablichj.incubator.uistate3.node.panel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PanelNode : BackStackNode<Node>(), ContainerNode {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private var activeNodeState: MutableState<Node?> = mutableStateOf(null)
    private var selectedIndex = 0
    private var navItems: MutableList<NodeItem> = mutableListOf()
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
        if (activeNodeState.value == null) {
            if (childNodesCopy.isNotEmpty()) {
                println("PanelNode::start() with selectedIndex = $selectedIndex")
                pushNode(childNodesCopy[selectedIndex])
            } else {
                println("PanelNode::start() with childNodes empty")
            }

        } else {
            println("PanelNode::start() with activeNodeState = ${activeNodeState.value?.clazz}")
            activeNodeState.value?.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNodeState.value?.stop()
    }

    // region BackStackNode override

    override fun onStackPush(oldTop: Node?, newTop: Node) {
        println(
            "PanelNode::onStackPop(), oldTop: ${oldTop?.let { it::class.simpleName }}," +
                    " newTop: ${newTop::class.simpleName}"
        )

        activeNodeState.value = newTop
        newTop.start()
        oldTop?.stop()

        updateSelectedNavItem(newTop)
    }

    override fun onStackPop(oldTop: Node, newTop: Node?) {
        println(
            "PanelNode::onStackPop(), oldTop: ${oldTop::class.simpleName}," +
                    " newTop: ${newTop?.let { it::class.simpleName }}"
        )

        activeNodeState.value = newTop
        newTop?.start()
        oldTop.stop()

        newTop?.let { updateSelectedNavItem(newTop) }
    }

    private fun updateSelectedNavItem(newTop: Node) {
        getNavItemFromNode(newTop)?.let {
            println("PanelNode::updateSelectedNavItem(), selectedIndex = $it")
            panelState.selectNavItem(it)
            selectedIndex = childNodes.indexOf(newTop)
        }
    }

    private fun getNavItemFromNode(node: Node): NodeItem? {
        return panelState.navItems.firstOrNull { it.node == node }
    }

    // endregion

    // region: INavigatorNode

    override fun getNode(): Node {
        return this
    }

    override fun getSelectedItemIndex(): Int {
        return selectedIndex
    }

    override fun setItems(
        navItemsList: MutableList<NodeItem>,
        selectedIndex: Int,
        isTransfer: Boolean
    ) {
        println("PanelNode::setItems() with selectedIndex = $selectedIndex")
        this.selectedIndex = selectedIndex

        navItems = navItemsList.map { it }.toMutableList()

        var selectedNodeFromTransfer : Node? = null
        this.childNodes = navItems.mapIndexed{ idx, navItem ->
            navItem.node.also {
                it.context.attachToParent(context)
                if (idx == selectedIndex) { selectedNodeFromTransfer = it }
            }
        }.toMutableList()

        panelState.navItems = navItems
        panelState.selectNavItem(navItems[selectedIndex])

        // If setItem() is called after start() was call, then we update the UI right here
        if (context.lifecycleState == LifecycleState.Started) {
            pushNode(childNodes[selectedIndex])
        } else {
            if (isTransfer) {
                activeNodeState.value = selectedNodeFromTransfer
            }
        }
    }

    override fun getItems(): MutableList<NodeItem> {
        return navItems
    }

    override fun addItem(nodeItem: NodeItem, index: Int) {
        navItems.add(index, nodeItem)
        childNodes.add(index, nodeItem.node)
        // The call to toMutableList() should return a new stack variable that triggers
        // recomposition in navDrawerState.
        panelState.navItems = navItems.toMutableList()
    }

    override fun removeItem(index: Int) {
        navItems.removeAt(index)
        childNodes.removeAt(index)
        // The call to toMutableList() should return a new stack variable that triggers
        // recomposition in navDrawerState.
        panelState.navItems = navItems.toMutableList()
    }

    override fun clearItems() {
        navItems.clear()
        childNodes.clear()
        stack.clear()
    }

    // endregion

    // region: DeepLink

    override fun getDeepLinkNodes(): List<Node> {
        return childNodes
    }

    override fun onDeepLinkMatchingNode(matchingNode: Node) {
        println("PanelNode.onDeepLinkMatchingNode() matchingNode = ${matchingNode.context.subPath}")
        pushNode(matchingNode)
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """PanelNode.Composing() stack.size = ${stack.size}
                |lifecycleState = ${context.lifecycleState}
            """.trimMargin()
        )

        NavigationPanel(
            modifier = modifier,
            panelState = panelState
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