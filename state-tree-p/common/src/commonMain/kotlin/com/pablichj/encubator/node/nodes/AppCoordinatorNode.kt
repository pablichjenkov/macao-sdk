package com.pablichj.encubator.node.nodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.encubator.node.BackStackNode
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AppCoordinatorNode(
    parentContext: NodeContext
) : BackStackNode<Node>(parentContext) {

    private val SplashNode = SplashNode(context) {
        pushNode(OnboardingNode)
    }

    private val OnboardingNode: Node = OnboardingNode(
        context,
        "Onboard",
        Icons.Filled.Home
    ) {
        pushNode(HomeNode)
    }

    lateinit var HomeNode: Node

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private var activeNode: Node? = null

    override fun start() {
        super.start()
        if (activeNode == null) {
            pushNode(SplashNode)
        } else {
            activeNode?.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNode?.stop()
    }

    /**
     * This class override the default handleBackPressed() behavior in BackStackNode
     * */
    override fun handleBackPressed() {

        // TODO: Replace this logic by a proper state machine state variable and not the class type
        when (val node = activeNode) {
            is SplashNode -> {

            }
            is OnboardingNode -> {
                delegateBackPressedToParent()
            }
            else -> {
                delegateBackPressedToParent()
            }
        }
    }

    override fun onStackTopChanged(node: Node) {
        activeNode = node
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            "AppCoordinatorNode::Composing AppCoordinatorNode.Content stackSize = ${stack.size}"
        )

        Box(modifier = Modifier.fillMaxSize()) {
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