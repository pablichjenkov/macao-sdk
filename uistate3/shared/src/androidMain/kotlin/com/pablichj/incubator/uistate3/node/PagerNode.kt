package com.pablichj.incubator.uistate3.node

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
class PagerNode : Node(), ContainerNode {

    //private var nodeCoroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    // Used to call functions in specific Composable's States that need a monotonic clock
    private lateinit var composableCoroutineScope: CoroutineScope
    private var activeNode: Node? = null
    private var selectedIndex = 0
    private var navItems: MutableList<NodeItem> = mutableListOf()
    private var childNodes: MutableList<Node> = mutableListOf(EmptyNode())
    private var pagerState = PagerState(selectedIndex)
    override val stack = ArrayDeque<Node>()

    override fun start() {
        super.start()
        val childNodesLocal = childNodes
        val activeNodeLocal = activeNode
        if (activeNodeLocal == null) {
            if (childNodesLocal.size > selectedIndex) {
                println("PagerNode::start() with selectedIndex = $selectedIndex")
                updateScreen()
            } else {
                println("PagerNode::start() childSize < selectedIndex BAD!")
            }
        } else {
            println("PagerNode::start() with activeNodeState = ${activeNodeLocal.clazz}")
            activeNodeLocal.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNode?.stop()
    }

    override fun handleBackPressed() {
        if (pagerState.currentPage > 0) {
            selectPage(pagerState.currentPage - 1)
        } else {
            delegateBackPressedToParent()
        }
    }

    // region: NavigatorNode

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
        this.selectedIndex = selectedIndex

        navItems = navItemsList.map { it }.toMutableList()

        this.childNodes = navItems.mapIndexed { idx, navItem ->
            navItem.node.also {
                it.attachToParent(this@PagerNode)
            }
        }.toMutableList()

        updateScreen()
    }

    override fun getItems(): MutableList<NodeItem> {
        return this.navItems
    }

    override fun addItem(nodeItem: NodeItem, index: Int) {
        childNodes.add(index, nodeItem.node)
        updateScreen()
    }

    override fun removeItem(index: Int) {
        childNodes.removeAt(index)
        updateScreen()
    }

    override fun clearItems() {
        childNodes.clear()
        childNodes.add(EmptyNode())
    }

    // endregion

    // region: DeepLink

    override fun getDeepLinkNodes(): List<Node> {
        return childNodes
    }

    override fun onDeepLinkMatchingNode(matchingNode: Node) {
        println("PagerNode.onDeepLinkMatchingNode() matchingNode = ${matchingNode.subPath}")
        val matchingNodeIndex = childNodes.indexOf(matchingNode)
        if (matchingNodeIndex > 0) {
            selectPage(matchingNodeIndex)
        }

    }

    // endregion

    private fun selectPage(pageIdx: Int) {
        composableCoroutineScope.launch {
            pagerState.animateScrollToPage(pageIdx)
        }
    }

    private fun updateScreen() {
        // Refresh the PagerState instance to trigger an update
        pagerState = PagerState(selectedIndex)
    }

    private fun onPageChanged(pageIndex: Int) {
        println("PagerNode::onPageChanged newPage = $pageIndex")
        // Lets dispatch the appropriate lifecycle events
        val previousNode = activeNode
        val currentNode = childNodes[pageIndex]

        previousNode?.stop()
        currentNode.start()

        selectedIndex = pageIndex
        activeNode = currentNode
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("PagerNode::Composing() currentPage = ${pagerState.currentPage}")
        println(
            """PagerNode.Composing() stack.size = ${stack.size}
                |currentPage = ${pagerState.currentPage}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )

        composableCoroutineScope = rememberCoroutineScope()

        Box {
            HorizontalPager(
                count = childNodes.size,
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, Color.Blue)
                    .padding(4.dp),
                state = pagerState
            ) { pageIndex ->
                childNodes[pageIndex].Content(modifier = modifier)
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 56.dp),
            )
        }

        LaunchedEffect(pagerState) {
            // Collect from the pager state a snapshotFlow reading the currentPage
            snapshotFlow { pagerState.currentPage }.collect { pageIndex ->
                onPageChanged(pageIndex)
            }
        }

    }

}

private class EmptyNode : Node() {
    @Composable
    override fun Content(modifier: Modifier) {
        Box {
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