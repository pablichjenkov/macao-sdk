package com.pablichj.incubator.uistate3.node.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult
import com.pablichj.incubator.uistate3.node.pager.indicator.DefaultPagerIndicator
import com.pablichj.incubator.uistate3.node.stack.BackStack
import com.pablichj.incubator.uistate3.platform.LocalSafeAreaInsets
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
    private var currentActiveIndexSet = mutableSetOf<Int>()
    private var pagerState = PagerState(selectedIndex)

    // Used to call functions in specific Composable's States that need a monotonic clock
    private lateinit var composableCoroutineScope: CoroutineScope

    override fun start() {
        super.start()
        println("$clazz::start()")
        if (currentActiveIndexSet.isEmpty()) {
            if (childComponents.isNotEmpty()) {
                activeComponent.value = childComponents[selectedIndex]
            } else {
                println("$clazz::start() with childComponents empty")
            }
        } else {
            currentActiveIndexSet.forEach { activeChildIndex ->
                childComponents[activeChildIndex].start()
            }
        }
    }

    override fun stop() {
        super.stop()
        println("$clazz::stop()")
        currentActiveIndexSet.forEach { activeChildIndex ->
            childComponents[activeChildIndex].stop()
        }
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
        activeComponent.value = childComponents[selectedIndex]
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
        val nextStartedIndexSet = mutableSetOf<Int>()
        if (pageIndex - 1 >= 0) {
            nextStartedIndexSet.add(pageIndex - 1)
        }
        nextStartedIndexSet.add(pageIndex)
        if (pageIndex + 1 < childComponents.size) {
            nextStartedIndexSet.add(pageIndex + 1)
        }

        // TODO: Extract this to a helper class and Unit test it
        println("onPageChanged::currentStartedSet = $currentActiveIndexSet")
        println("onPageChanged::nextStartedSet = $nextStartedIndexSet")
        val keepStartedIndexSet = mutableSetOf<Int>()
        currentActiveIndexSet.forEach { index ->
            if (nextStartedIndexSet.contains(index)) {
                keepStartedIndexSet.add(index)
            } else {
                println("onPageChanged::stopping old index = $index")
                childComponents[index].stop()
            }
        }

        println("onPageChanged::keepStartedIndexSet = $keepStartedIndexSet")
        val newStartingSet = nextStartedIndexSet.subtract(keepStartedIndexSet)
        println("onPageChanged::newStartingSet = $newStartingSet")
        newStartingSet.forEach { index ->
            childComponents[index].start()
        }

        currentActiveIndexSet = nextStartedIndexSet
        selectedIndex = pageIndex
        activeComponent.value = childComponents[pageIndex]
        updateBackstack(pageIndex)
    }

    private fun updateBackstack(selectedIndex: Int) {
        backStack.deque.clear()
        backStack.deque.add(childComponents[selectedIndex])
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """PagerComponent.Composing() stack.size = ${backStack.size()}
                |currentPage = ${pagerState.currentPage}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )

        val pagerItemsSize = childComponents.size
        composableCoroutineScope = rememberCoroutineScope()
        val safeAreaInsets = LocalSafeAreaInsets.current

        Box {
            if(activeComponent.value != null) {
                HorizontalPager(
                    pageCount = pagerItemsSize,
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) { pageIndex ->
                    println("HorizontalPager::pageIndex = $pageIndex")
                    childComponents[pageIndex].Content(modifier = modifier)
                }
                DefaultPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = safeAreaInsets.top.dp.plus(56.dp)),
                    pagerState = pagerState,
                    itemCount = pagerItemsSize,
                    indicatorCount = pagerItemsSize
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "$clazz Empty Stack, Please add some children",
                    textAlign = TextAlign.Center
                )
            }
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
