package com.pablichj.encubator.node.topbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.encubator.node.BackStackNode
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext

@OptIn(ExperimentalMaterial3Api::class)
open class TopBarBackStackNode<T : Node>(
    parentContext: NodeContext
) : BackStackNode<T>(parentContext) {

    protected val topBarState = TopBarState()

    override fun onStackTopChanged(node: T) {
        if (stack.size > 1) {
            setTitleSectionForBackClick(node)
        } else {
            setTitleSectionForHomeClick(node)
        }
    }

    protected open fun setTitleSectionForHomeClick(node: T) {
    }

    protected open fun setTitleSectionForBackClick(node: T) {
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("TopBarBackStackNode::Composing TopBarBackStackNode.Content stackSize = ${stack.size}")

        Scaffold(
            modifier = modifier,
            topBar = { TopBar(topBarState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, Color.Green)
                    .padding(paddingValues)
            ) {
                if (screenUpdateCounter >= 0 && stack.size > 0) {
                    stack.peek().Content(Modifier)
                } else {
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
    }

}