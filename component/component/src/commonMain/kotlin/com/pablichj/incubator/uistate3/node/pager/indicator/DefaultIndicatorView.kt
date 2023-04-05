package com.pablichj.incubator.uistate3.node.pager.indicator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.pager.IndicatorOrientation

/**
 * Creates indicator  aligned horizontally or verticall based on [orientation]
 * for [HorizontalPager] or [VerticalPager] with [indicatorCount]
 * with option to click when [onClick]
 * is set.
 *
 * @param pagerState state of pager
 * @param indicatorCount counter of visible indicator items
 * @param indicatorSize size of each indicator item
 * @param indicatorShape shape of the indicator items, by default it's [CircleShape]
 * @param space between indicator items
 * @param activeColor color of indicator when current page points to this item
 * @param inActiveColor color for indicators that are not active
 * @param orientation determines whether items should be aligned horizontally or vertically
 * @param onClick action that should be taken when any indicator items are touched
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DefaultPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    itemCount: Int,
    indicatorCount: Int,
    indicatorSize: Dp = 16.dp,
    indicatorShape: Shape = CircleShape,
    space: Dp = 8.dp,
    activeColor: Color = Color(0xffEC407A),
    inActiveColor: Color = Color.LightGray,
    orientation: IndicatorOrientation = IndicatorOrientation.Horizontal,
    onClick: ((Int) -> Unit)? = null
) {
    val listState = rememberLazyListState()

    val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

    val currentItem by remember(pagerState) {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    LaunchedEffect(key1 = currentItem) {
        val viewportSize = listState.layoutInfo.viewportSize
        if (orientation == IndicatorOrientation.Horizontal) {
            listState.animateScrollToItem(
                currentItem,
                (widthInPx / 2 - viewportSize.width / 2).toInt()
            )
        } else {
            listState.animateScrollToItem(
                currentItem,
                (widthInPx / 2 - viewportSize.height / 2).toInt()
            )
        }
    }

    if (orientation == IndicatorOrientation.Horizontal) {
        LazyRow(
            modifier = modifier.width(totalWidth),
            state = listState,
            contentPadding = PaddingValues(vertical = space),
            horizontalArrangement = Arrangement.spacedBy(space),
            userScrollEnabled = false
        ) {
            indicatorItems(
                itemCount,
                currentItem,
                indicatorCount,
                indicatorShape,
                activeColor,
                inActiveColor,
                indicatorSize,
                onClick
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.height(totalWidth),
            state = listState,
            contentPadding = PaddingValues(horizontal = space),
            verticalArrangement = Arrangement.spacedBy(space),
            userScrollEnabled = false
        ) {
            indicatorItems(
                itemCount,
                currentItem,
                indicatorCount,
                indicatorShape,
                activeColor,
                inActiveColor,
                indicatorSize,
                onClick
            )
        }
    }

}

private fun LazyListScope.indicatorItems(
    itemCount: Int,
    currentItem: Int,
    indicatorCount: Int,
    indicatorShape: Shape,
    activeColor: Color,
    inActiveColor: Color,
    indicatorSize: Dp,
    onClick: ((Int) -> Unit)?
) {
    items(itemCount) { index ->

        val isSelected = (index == currentItem)

        // Index of item in center when odd number of indicators are set
        // for 5 indicators this is 2nd indicator place
        val centerItemIndex = indicatorCount / 2

        val right1 =
            (currentItem < centerItemIndex &&
                    index >= indicatorCount - 1)

        val right2 =
            (currentItem >= centerItemIndex &&
                    index >= currentItem + centerItemIndex &&
                    index < itemCount - centerItemIndex + 1)
        val isRightEdgeItem = right1 || right2

        // Check if this item's distance to center item is smaller than half size of
        // the indicator count when current indicator at the center or
        // when we reach the end of list. End of the list only one item is on edge
        // with 10 items and 7 indicators
        // 7-3= 4th item can be the first valid left edge item and
        val isLeftEdgeItem =
            index <= currentItem - centerItemIndex &&
                    currentItem > centerItemIndex &&
                    index < itemCount - indicatorCount + 1

        Box(
            modifier = Modifier
                .graphicsLayer {
                    val scale = if (isSelected) {
                        1f
                    } else if (isLeftEdgeItem || isRightEdgeItem) {
                        .5f
                    } else {
                        .8f
                    }
                    scaleX = scale
                    scaleY = scale

                }

                .clip(indicatorShape)
                .size(indicatorSize)
                .background(
                    if (isSelected) activeColor else inActiveColor,
                    indicatorShape
                )
                .then(
                    if (onClick != null) {
                        Modifier
                            .clickable {
                                onClick.invoke(index)
                            }
                    } else Modifier
                )
        )
    }
}
