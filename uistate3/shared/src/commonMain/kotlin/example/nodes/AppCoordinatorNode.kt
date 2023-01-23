package example.nodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.BackStackNode
import com.pablichj.incubator.uistate3.node.Node
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AppCoordinatorNode : BackStackNode<Node>() {

    private val SplashNode = SplashNode {
        pushNode(TopBarNode)
    }.also { it.context.attachToParent(this@AppCoordinatorNode.context) }

    private val TopBarNode: Node = TopBarNode(
        "Onboard",
        Icons.Filled.Home
    ) {
        pushNode(HomeNode)
    }.also { it.context.attachToParent(this@AppCoordinatorNode.context) }

    //todo: Use setHomeNode instead, and attach to parent context, see SplitNode class as example
    lateinit var HomeNode: Node

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private var activeNodeState: MutableState<Node?> = mutableStateOf(null)

    override fun start() {
        super.start()
        if (activeNodeState.value == null) {
            pushNode(SplashNode)
        } else {
            activeNodeState.value?.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNodeState.value?.stop()
    }

    override fun onStackPush(oldTop: Node?, newTop: Node) {
        activeNodeState.value = newTop
        newTop.start()
        oldTop?.stop()
    }

    override fun onStackPop(oldTop: Node, newTop: Node?) {
        activeNodeState.value = newTop
        newTop?.start()
        oldTop.stop()
    }

    /**
     * This class override the default handleBackPressed() behavior in BackStackNode
     * */
    override fun handleBackPressed() {

        // TODO: Replace this logic by a proper state machine state variable and not the class type
        when (val node = activeNodeState.value) {
            is SplashNode -> {

            }
            is TopBarNode -> {
                delegateBackPressedToParent()
            }
            else -> {
                delegateBackPressedToParent()
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            "AppCoordinatorNode::Composing() stack.size = ${stack.size}"
        )

        Box(modifier = Modifier.fillMaxSize()) {
            val activeNodeUpdate = activeNodeState.value
            if (activeNodeUpdate != null && stack.size > 0) {
                activeNodeUpdate.Content(Modifier)
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