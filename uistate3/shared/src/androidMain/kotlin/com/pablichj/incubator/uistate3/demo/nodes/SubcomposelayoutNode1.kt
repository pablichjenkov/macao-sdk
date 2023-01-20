package com.pablichj.incubator.uistate3.demo.nodes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.node.NodeContext

data class UiModel(val lines: Int)

class SubcomposeLayout1Node : Node() {
    @Composable
    override fun Content(modifier: Modifier) {
        MyCoolGrid(
            modifier = modifier,
            list = listOf(
                UiModel(7),
                UiModel(14),
                UiModel(2),
                UiModel(10)
            )
        )
    }
}

@Composable
fun MyCoolGrid(modifier: Modifier, list: List<UiModel>) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val measuredHeights = subcompose(SlotsEnum.Height) {
            list.map { TestItem(countText = it.lines, minHeight = 0.dp) }
        }.map {
            val measure = it.measure(constraints)
            val measuredHeight = measure.measuredHeight.toDp()
            logD("measuredheight $measuredHeight")
            measuredHeight
        }
        val maxHeight = measuredHeights.maxOrNull() ?: 0.dp // get the max height from all items!!

        // Measure the LazyVerticalGrid
        val contentPlaceable = subcompose(SlotsEnum.Content) {
            androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                modifier = Modifier,
                columns = GridCells.Fixed(2),
            ) {
                itemsIndexed(list) { _, item ->
                    TestItem(
                        text = "$maxHeight",
                        countText = item.lines,
                        minHeight = maxHeight // We are setting the max height to each item!!
                    )
                }
            }
        }.first().measure(constraints)

        logD("Whole LazyVerticalGrid component measured ${contentPlaceable.measuredHeight.toDp()}")
        layout(contentPlaceable.width, contentPlaceable.height) {
            // Place the LazyVerticalGrid with its content
            contentPlaceable.place(0, 0)
        }
    }
}

private fun logD(text: String) {
    Log.d("SomeCoolGrid", "$text")
}

private enum class SlotsEnum {
    Height, Width, Content // You could also measure Width
}

@Composable
fun TestItem(text: String = "This is a line", countText: Int, minHeight: Dp = 0.dp) {
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = minHeight)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.Red)

    ) {
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .height(128.dp)
                .fillMaxWidth()
        )
//        Spacer(modifier = Modifier.weight(1f))
        repeat(countText) {
            Text(
                text = "$text",
            )
        }
    }
}