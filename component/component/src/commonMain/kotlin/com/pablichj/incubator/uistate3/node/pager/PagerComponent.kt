package com.pablichj.incubator.uistate3.node.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.ComponentLifecycleState
import com.pablichj.incubator.uistate3.node.NavComponent
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult
import com.pablichj.incubator.uistate3.node.stack.BackStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The PagerComponent keeps 3 started children components at the same time. It is necessary
 * as a warmup so the next Component.Content is started already when the user swipe.
 * */
@OptIn(ExperimentalFoundationApi::class)
open class PagerComponent(
    private val config: Config = Config()
) : Component(), NavComponent {
    override val backStack = BackStack<Component>()
    override var navItems: MutableList<NavItem> = mutableListOf()
    final override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private var pagerState = PagerState(selectedIndex)
    private var currentStartedIndexList = mutableListOf<Int>()

    // Used to call functions in specific Composable's States that need a monotonic clock
    private lateinit var composableCoroutineScope: CoroutineScope

    override fun start() {
        super.start()
        if (activeComponent.value != null) {
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
        this.selectedIndex = selectedIndex
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

    private fun onPageChanged(pageIndex: Int) {
        println("PagerComponent::onPageChanged newPage = $pageIndex")

        if (pageIndex >= childComponents.size) return

        val nextStartedIndexList = mutableListOf<Int>()
        if (pageIndex - 1 >= 0) {
            nextStartedIndexList.add(pageIndex - 1)
        }
        nextStartedIndexList.add(pageIndex)
        if (pageIndex + 1 < childComponents.size) {
            nextStartedIndexList.add(pageIndex + 1)
        }

        // TODO: Extract this to a helper class and Unit test it
        println("onPageChanged::currentStartedList = $currentStartedIndexList")
        println("onPageChanged::nextStartedList = $nextStartedIndexList")
        val repeatedIndexList = mutableListOf<Int>()
        currentStartedIndexList.forEach { index ->
            if (nextStartedIndexList.contains(index)) {
                repeatedIndexList.add(index)
            } else {
                println("onPageChanged::stopping old index = $index")
                childComponents[index].stop()
            }
        }

        println("onPageChanged::repeatedIndexList = $repeatedIndexList")
        val newStartingList = nextStartedIndexList.subtract(repeatedIndexList)
        println("onPageChanged::newStartingList = $newStartingList")
        newStartingList.forEach { index ->
            childComponents[index].start()
        }

        currentStartedIndexList = nextStartedIndexList
        selectedIndex = pageIndex
        activeComponent.value = childComponents[pageIndex]
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """PagerComponent.Composing() stack.size = ${backStack.size()}
                |currentPage = ${pagerState.currentPage}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )

        composableCoroutineScope = rememberCoroutineScope()

        Box {
            HorizontalPager(
                pageCount = childComponents.size,
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) { pageIndex ->
                println("HorizontalPager::pageIndex = $pageIndex")
                childComponents[pageIndex].Content(modifier = modifier)
            }
            /*HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 56.dp),
            )*/
        }

        LaunchedEffect(pagerState) {
            // Collect from the pager state a snapshotFlow reading the settledPage
            snapshotFlow { pagerState.settledPage }.collect { pageIndex ->
                onPageChanged(pageIndex)
            }
        }

    }

    class Config(
        var pagerStyle: PagerStyle = PagerStyle()
    )

}
