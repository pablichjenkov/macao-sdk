package example.nodes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.BackStackNode
import com.pablichj.incubator.uistate3.node.ContainerNode
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.navigation.SubPath
import com.pablichj.incubator.uistate3.node.topbar.TitleSectionStateHolder
import com.pablichj.incubator.uistate3.node.topbar.TopBar
import com.pablichj.incubator.uistate3.node.topbar.TopBarState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class OnboardingNode(
    val screenName: String,
    val screenIcon: ImageVector? = null,
    val onMessage: (Msg) -> Unit
) : BackStackNode<OnboardingStepNode>() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private val topBarState = TopBarState()
    private var activeNodeState: MutableState<OnboardingStepNode?> = mutableStateOf(null)

    val Step1 = OnboardingStepNode(
        "$screenName / Page 1",
        Color.Yellow
    ) { msg ->
        when (msg) {
            OnboardingStepNode.Msg.Next -> {
                pushNode(Step2)
            }
        }
    }.also {
        it.context.attachToParent(this@OnboardingNode.context)
        it.context.subPath = SubPath("Page1")
    }

    val Step2 = OnboardingStepNode(
        "$screenName / Page 1 / Page 2",
        Color.Green
    ) { msg ->
        when (msg) {
            OnboardingStepNode.Msg.Next -> {
                pushNode(Step3)
            }
        }
    }.also {
        it.context.attachToParent(this@OnboardingNode.context)
        it.context.subPath = SubPath("Page2")
    }

    val Step3 =
        OnboardingStepNode(
            "$screenName / Page 1 / Page 2 / Page 3",
            Color.Cyan
        ) { msg ->
            when (msg) {
                OnboardingStepNode.Msg.Next -> {
                    onMessage(Msg.OnboardDone)
                }
            }
        }.also {
            it.context.attachToParent(this@OnboardingNode.context)
            it.context.subPath = SubPath("Page3")
        }

    override fun start() {
        super.start()
        if (activeNodeState.value == null) {
            pushNode(Step1)
        } else {
            activeNodeState.value?.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNodeState.value?.stop()
    }

    override fun onStackPush(oldTop: OnboardingStepNode?, newTop: OnboardingStepNode) {
        activeNodeState.value = newTop
        newTop.start()
        oldTop?.stop()

        if (stack.size > 1) {
            setTitleSectionForBackClick(newTop)
        } else {
            setTitleSectionForHomeClick(newTop)
        }
    }

    override fun onStackPop(oldTop: OnboardingStepNode, newTop: OnboardingStepNode?) {
        activeNodeState.value = newTop
        newTop?.start()
        oldTop.stop()

        if (newTop != null) {
            if (stack.size > 1) {
                setTitleSectionForBackClick(newTop)
            } else {
                setTitleSectionForHomeClick(newTop)
            }
        }

    }

    private fun setTitleSectionForHomeClick(node: OnboardingStepNode) {
        topBarState.setTitleSectionState(
            TitleSectionStateHolder(
                title = node.text,
                icon1 = resolveFirstIcon(),
                onIcon1Click = {
                    context.findClosestNavigationProvider()?.open()
                },
                onTitleClick = {
                    context.findClosestNavigationProvider()?.open()
                }
            )
        )
    }

    private fun setTitleSectionForBackClick(node: OnboardingStepNode) {
        topBarState.setTitleSectionState(
            TitleSectionStateHolder(
                title = node.text,
                onTitleClick = {
                    handleBackPressed()
                },
                icon1 = resolveFirstIcon(),
                onIcon1Click = {
                    context.findClosestNavigationProvider()?.open()
                },
                icon2 = Icons.Filled.ArrowBack,
                onIcon2Click = {
                    handleBackPressed()
                }
            )
        )
    }

    private fun resolveFirstIcon(): ImageVector? {
        val canProvideGlobalNavigation = context.findClosestNavigationProvider() != null
        return if (canProvideGlobalNavigation) {
            Icons.Filled.Menu
        } else {
            screenIcon
        }
    }

    // region: DeepLink

    override fun getDeepLinkNodes(): List<Node> {
        return listOf(Step1, Step2, Step3)
    }

    override fun onDeepLinkMatchingNode(matchingNode: Node) {
        println("OnboardingNode.onDeepLinkMatchingNode() matchingNode = ${matchingNode.context.subPath}")
        pushNode(matchingNode as OnboardingStepNode) //todo: see how get rid of the cast
    }

    // endregion

    sealed interface Msg {
        object OnboardDone : Msg
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("OnboardingNode::Composing(), stack.size = ${stack.size}")

        Scaffold (
            modifier = modifier,
            topBar = { TopBar(topBarState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, Color.Green)
                    .padding(paddingValues)
            ) {
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

}