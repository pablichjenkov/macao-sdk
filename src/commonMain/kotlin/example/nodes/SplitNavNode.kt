package com.pablichj.incubator.uistate3.node.nodes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.node.NodeContext

class SplitNavNode(
    parentContext: NodeContext
) : Node(parentContext) {

    var TopNode: Node? = null
    var BottomNode: Node? = null

    override fun start() {
        super.start()
        println("SplitNavNode::start")
        TopNode?.start()
        BottomNode?.start()
    }

    override fun stop() {
        super.stop()
        println("SplitNavNode::stop")
        TopNode?.stop()
        BottomNode?.stop()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("SplitNavNode::Composing()")
        Column(modifier = Modifier.fillMaxSize()) {
            val TopNodeCopy = TopNode
            if (TopNodeCopy != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5F)
                        .padding(start = 40.dp, top = 40.dp, end = 40.dp, bottom = 20.dp)
                ) {
                    TopNodeCopy.Content(Modifier)
                }
            }

            val BottomNodeCopy = BottomNode
            if (BottomNodeCopy != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 40.dp, top = 20.dp, end = 40.dp, bottom = 40.dp)
                ) {
                    BottomNodeCopy.Content(Modifier)
                }
            }
        }
    }
}