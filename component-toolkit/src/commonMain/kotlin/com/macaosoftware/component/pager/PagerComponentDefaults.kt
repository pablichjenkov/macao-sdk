package com.macaosoftware.component.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.pager.indicator.DefaultPagerIndicator
import kotlinx.coroutines.launch

object PagerComponentDefaults {

    fun createComponentViewModel(): PagerComponentDefaultViewModel {
        return PagerComponentDefaultViewModel()
    }

    @OptIn(ExperimentalFoundationApi::class)
    val PagerComponentView: @Composable PagerComponent.(
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
