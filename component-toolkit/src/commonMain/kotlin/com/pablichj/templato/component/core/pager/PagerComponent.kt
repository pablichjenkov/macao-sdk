package com.pablichj.templato.component.core.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.componentWithBackStackGetChildForNextUriFragment
import com.pablichj.templato.component.core.destroyChildComponent
import com.pablichj.templato.component.core.pager.indicator.DefaultPagerIndicator
import com.pablichj.templato.component.core.stack.AddAllPushStrategy
import com.pablichj.templato.component.core.stack.PushStrategy
import com.pablichj.templato.component.core.util.EmptyNavigationComponentView
import com.pablichj.templato.component.platform.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * The PagerComponent keeps 3 started children components at the same time. It is necessary
 * as a warmup so the next Component.Content is started already when the user swipe.
 * */
@OptIn(ExperimentalFoundationApi::class)
class PagerComponent(
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
    val pagerStyle: PagerStyle = PagerStyle(),
    dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    private var content: @Composable PagerComponent.(
        modifier: Modifier,
        pagerState: PagerState,
        childComponents: List<Component>
    ) -> Unit
) : Component(), NavigationComponent {

    override val backStack = createBackStack(pushStrategy)
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private var currentActiveIndexSet = mutableSetOf<Int>()
    private var coroutineScope = CoroutineScope(dispatchers.main)

    private val _componentOutFlow = MutableSharedFlow<PagerComponentOutEvent?>()
    val pagerComponentViewFlow: SharedFlow<PagerComponentOutEvent?>
        get() = _componentOutFlow

    private var currentPage = 0

    override fun onStart() {
        println("${instanceId()}::onStart()")
        if (currentActiveIndexSet.isEmpty()) {
            if (childComponents.isNotEmpty()) {
                if (getComponent().startedFromDeepLink) {
                    return
                }
                activeComponent.value = childComponents[selectedIndex]
            } else {
                println("${instanceId()}::onStart() with childComponents empty")
            }
        } else {
            currentActiveIndexSet.forEach { activeChildIndex ->
                childComponents[activeChildIndex].dispatchStart()
            }
        }
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
        currentActiveIndexSet.forEach { activeChildIndex ->
            childComponents[activeChildIndex].dispatchStop()
        }
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun handleBackPressed() {
        if (currentPage > 0) {
            selectPage(currentPage - 1)
        } else {
            delegateBackPressedToParent()
        }
    }

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        activeComponent.value = childComponents[selectedIndex]
    }

    override fun updateSelectedNavItem(newTop: Component) {
        // The ViewPager updates the indicator automatically, no need to do anything here.
    }

    override fun onDestroyChildComponent(component: Component) {
        destroyChildComponent()
    }

    // endregion

    // region: DeepLink

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        val matchingComponentIndex = childComponents.indexOf(matchingComponent)
        selectPage(matchingComponentIndex)
        return DeepLinkResult.Success(matchingComponent)
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return (this as ComponentWithBackStack).componentWithBackStackGetChildForNextUriFragment(nextUriFragment)
    }

    // endregion

    private fun selectPage(pageIdx: Int) {
        coroutineScope.launch {
            _componentOutFlow.emit(PagerComponentOutEvent.SelectPage(pageIdx))
        }
    }

    private fun onPageChanged(pageIndex: Int) {
        println("${instanceId()}::onPageChanged newPage = $pageIndex")
        currentPage = pageIndex
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
                childComponents[index].dispatchStop()
            }
        }

        println("onPageChanged::keepStartedIndexSet = $keepStartedIndexSet")
        val newStartingSet = nextStartedIndexSet.subtract(keepStartedIndexSet)
        println("onPageChanged::newStartingSet = $newStartingSet")
        newStartingSet.forEach { index ->
            childComponents[index].dispatchStart()
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

    // region Pager rendering

    @Composable
    override fun Content(modifier: Modifier) {
        val pagerState = rememberPagerState(initialPage = selectedIndex) {
            childComponents.size
        }
        println(
            """${instanceId()}.Composing() stack.size = ${backStack.size()}
                |currentPage = ${pagerState.currentPage}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )
        if (activeComponent.value != null) {
            content(modifier, pagerState, childComponents)
        } else {
            EmptyNavigationComponentView(this@PagerComponent)
        }
        LaunchedEffect(childComponents, pagerState) {
            launch {
                // Collect from the pager state a snapshotFlow reading the settledPage
                snapshotFlow { pagerState.settledPage }.collect { pageIndex ->
                    onPageChanged(pageIndex)
                }
            }
        }
    }

    companion object {

        val DefaultPagerComponentView: @Composable PagerComponent.(
            modifier: Modifier,
            pagerState: PagerState,
            childComponents: List<Component>
        ) -> Unit = { modifier, pagerState, childComponents ->
            val pagerItemsSize = childComponents.size
            Box {
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) { pageIndex ->
                    println("HorizontalPager::pageIndex = $pageIndex")
                    childComponents[pageIndex].Content(modifier = modifier)
                }
                DefaultPagerIndicator(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 56.dp),
                    pagerState = pagerState,
                    itemCount = pagerItemsSize,
                    indicatorCount = pagerItemsSize
                )
            }
            LaunchedEffect(childComponents, pagerState) {
                launch {
                    pagerComponentViewFlow.collect {
                        when (val componentEvent = it) {
                            is PagerComponentOutEvent.SelectPage -> {
                                pagerState.animateScrollToPage(componentEvent.page)
                            }

                            null -> { /* NoOp */
                            }
                        }
                    }
                }
            }
        }
    }

}
