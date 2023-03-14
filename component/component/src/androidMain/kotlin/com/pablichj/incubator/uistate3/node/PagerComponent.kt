package com.pablichj.incubator.uistate3.node

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.pablichj.incubator.uistate3.node.backstack.BackStack
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
open class PagerComponent : Component(), NavComponent {
    override val backStack = BackStack<Component>()
    override var navItems: MutableList<NavItem> = mutableListOf()
    final override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private var pagerState = PagerState(selectedIndex)

    // Used to call functions in specific Composable's States that need a monotonic clock
    private lateinit var composableCoroutineScope: CoroutineScope

    override fun start() {
        super.start()
        val childNodesLocal = childComponents
        val activeNodeLocal = activeComponent.value
        if (activeNodeLocal == null) {
            if (childNodesLocal.size > selectedIndex) {
                println("$clazz::start(). Pushing selectedIndex = $selectedIndex")
                updateScreen()
            } else {
                println("$clazz::start() childSize(${childNodesLocal.size}) < selectedIndex($selectedIndex) BAD!")
            }
        } else {
            println("$clazz::start() with activeNodeState = ${activeNodeLocal.clazz}")
            activeNodeLocal.start()
        }
    }

    override fun stop() {
        super.stop()
        println("$clazz::stop()")
        activeComponent.value?.stop()
    }

    override fun handleBackPressed() {
        if (pagerState.currentPage > 0) {
            selectPage(pagerState.currentPage - 1)
        } else {
            delegateBackPressedToParent()
        }
    }

    // region: NavigatorItemsManagerNode

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
            backStack.push(childComponents[selectedIndex])
        }
    }

    /**
     * TODO: Try to update the navitem instead, using a Backstack<NavItem>, sounds more efficient
     * */
    override fun updateSelectedNavItem(newTop: Component) {
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
        val matchingNodeIndex = childComponents.indexOf(matchingComponent)
        selectPage(matchingNodeIndex)
        return DeepLinkResult.Success
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
        val previousNode = activeComponent
        val currentNode = childComponents[pageIndex]

        previousNode.value?.stop()
        currentNode.start()

        selectedIndex = pageIndex
        activeComponent.value = currentNode
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("PagerNode::Composing() currentPage = ${pagerState.currentPage}")
        println(
            """PagerNode.Composing() stack.size = ${backStack.size()}
                |currentPage = ${pagerState.currentPage}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )

        composableCoroutineScope = rememberCoroutineScope()

        Box {
            HorizontalPager(
                count = childComponents.size,
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) { pageIndex ->
                childComponents[pageIndex].Content(modifier = modifier)
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