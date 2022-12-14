package com.pablichj.encubator.node

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import java.util.*

// TODO: Add indicator
@OptIn(ExperimentalPagerApi::class)
class PagerNode(
    parentContext: NodeContext
) : Node(parentContext), NavigatorNode {

    //private var nodeCoroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    // Used to call functions in specific Composable's States that need a monotonic clock
    private lateinit var composableCoroutineScope: CoroutineScope
    private var activeNode: Node? = null
    private var startingIndex = 0
    private var selectedIndex = 0
    private var navItems: MutableList<NavigatorNodeItem> = mutableListOf()
    private var childNodes: MutableList<Node> = mutableListOf(EmptyNode(context))
    private var pagerState = PagerState(startingIndex)
    override val stack = Stack<Node>()

    override fun start() {
        super.start()
        activeNode?.start()
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

        updateScreen()
    }

    override fun getNavItems(): MutableList<NavigatorNodeItem> {
        return this.navItems
    }

    override fun addNavItem(navItem: NavigatorNodeItem, index: Int) {
        childNodes.add(index, navItem.node)
        updateScreen()
    }

    override fun removeNavItem(index: Int) {
        childNodes.removeAt(index)
        updateScreen()
    }

    override fun clearNavItems() {
        childNodes.clear()
        childNodes.add(EmptyNode(context))
    }

    // endregion

    // region: DeepLink

    override fun getDeepLinkNodes(): List<Node> {
        return childNodes
    }

    override fun onDeepLinkMatchingNode(matchingNode: Node) {
        println("PagerNode.onDeepLinkMatchingNode() matchingNode = ${matchingNode.context.subPath}")
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
        pagerState = PagerState(startingIndex)
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
                |lifecycleState = ${context.lifecycleState}
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

private class EmptyNode(parentContext: NodeContext) : Node(parentContext) {
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