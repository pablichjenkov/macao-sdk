package com.pablichj.encubator.node.nodes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.topbar.TitleSectionStateHolder
import com.pablichj.encubator.node.topbar.TopBarBackStackNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class OnboardingNode(
    parentContext: NodeContext,
    val screenName: String,
    val screenIcon: ImageVector? = null,
    val onMessage: (Msg) -> Unit
) : TopBarBackStackNode<OnboardingStepNode>(parentContext) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private var activeNode: OnboardingStepNode? = null

    val Step1 = OnboardingStepNode(context, "$screenName / Page 1", Color.Yellow) { msg ->
        when (msg) {
            OnboardingStepNode.Msg.Next -> {
                pushNode(Step2)
            }
        }
    }

    val Step2 = OnboardingStepNode(context, "$screenName / Page 1 / Page 2", Color.Green) { msg ->
        when (msg) {
            OnboardingStepNode.Msg.Next -> {
                pushNode(Step3)
            }
        }
    }

    val Step3 = OnboardingStepNode(context, "$screenName / Page 1 / Page 2 / Page 3", Color.Cyan) { msg ->
        when (msg) {
            OnboardingStepNode.Msg.Next -> {
                onMessage(Msg.OnboardDone)
            }
        }
    }

    override fun start() {
        super.start()
        if (activeNode == null) {
            pushNode(Step1)
        } else {
            activeNode?.start()
        }
    }

    override fun stop() {
        super.stop()
        activeNode?.stop()
    }

    override fun onStackTopChanged(node: OnboardingStepNode) {
        super.onStackTopChanged(node)
        activeNode = node
    }

    override fun setTitleSectionForHomeClick(node: OnboardingStepNode) {
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

    override fun setTitleSectionForBackClick(node: OnboardingStepNode) {
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


    sealed interface Msg {
        object OnboardDone : Msg
    }

}