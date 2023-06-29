package com.pablichj.templato.component.core.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.pager.indicator.DefaultPagerIndicator
import com.pablichj.templato.component.core.router.DeepLinkResult
import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.LocalSafeAreaInsets
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.NavigationComponent
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
    private val config: Config,
    private var diContainer: DiContainer
) : Component(), NavigationComponent {
    override val backStack = BackStack<Component>()
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var selectedIndex: Int = 0
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private var currentActiveIndexSet = mutableSetOf<Int>()
    val pagerState = PagerState(selectedIndex)
    private val coroutineScope = CoroutineScope(diContainer.dispatchers.main)

    private val _componentOutFlow = MutableSharedFlow<PagerComponentOutEvent?>()
    val pagerComponentViewFlow: SharedFlow<PagerComponentOutEvent?>
        get() = _componentOutFlow

    override fun onStart() {
        println("$clazz::onStart()")
        if (currentActiveIndexSet.isEmpty()) {
            if (childComponents.isNotEmpty()) {
                activeComponent.value = childComponents[selectedIndex]
            } else {
                println("$clazz::onStart() with childComponents empty")
            }
        } else {
            currentActiveIndexSet.forEach { activeChildIndex ->
                childComponents[activeChildIndex].dispatchStart()
            }
        }
    }

    override fun onStop() {
        println("$clazz::onStop()")
        currentActiveIndexSet.forEach { activeChildIndex ->
            childComponents[activeChildIndex].dispatchStop()
        }
        coroutineScope.coroutineContext.cancelChildren()
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

    override fun updateSelectedNavItem(newTop: Component) {
        // The ViewPager updates the indicator automatically, no need to do anything here.
    }

    override fun onDestroyChildComponent(component: Component) {
        if (component.lifecycleState == ComponentLifecycleState.Started) {
            component.dispatchStop()
            component.dispatchDestroy()
        } else {
            component.dispatchDestroy()
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
        coroutineScope.launch {
            _componentOutFlow.emit(PagerComponentOutEvent.SelectPage(pageIdx))
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

    fun setPagerComponentView(
        pagerComponentView: @Composable PagerComponent.(
            modifier: Modifier,
            childComponents: List<Component>
        ) -> Unit
    ) {
        this.pagerComponentView = pagerComponentView
    }

    private var pagerComponentView: @Composable PagerComponent.(
        modifier: Modifier,
        childComponents: List<Component>
    ) -> Unit = DefaultPagerComponentView

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """PagerComponent.Composing() stack.size = ${backStack.size()}
                |currentPage = ${pagerState.currentPage}
                |lifecycleState = ${lifecycleState}
            """.trimMargin()
        )
        if (activeComponent.value != null) {
            pagerComponentView(modifier, childComponents)
        } else {
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                text = "$clazz Empty Stack, Please add some children",
                textAlign = TextAlign.Center
            )
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

    class Config(
        var pagerStyle: PagerStyle = PagerStyle()
    )

    companion object {
        val DefaultConfig = Config(
            PagerStyle()
        )
        val DefaultPagerComponentView: @Composable PagerComponent.(
            modifier: Modifier,
            childComponents: List<Component>
        ) -> Unit = { modifier, childComponents ->
            val pagerItemsSize = childComponents.size
            val safeAreaInsets = LocalSafeAreaInsets.current
            Box {
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
            }
            LaunchedEffect(childComponents, pagerState) {
                launch {
                    pagerComponentViewFlow.collect {
                        when (val componentEvent = it) {
                            is PagerComponentOutEvent.SelectPage -> {
                                pagerState.animateScrollToPage(componentEvent.page)
                            }
                            null -> { /* NoOp */ }
                        }
                    }
                }
            }
        }
    }

}
