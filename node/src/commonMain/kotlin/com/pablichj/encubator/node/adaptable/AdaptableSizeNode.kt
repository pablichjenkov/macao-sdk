package com.pablichj.encubator.node.adaptable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.encubator.node.*
import com.pablichj.encubator.node.navigation.DeepLinkResult
import com.pablichj.encubator.node.navigation.Path

/**
 * This node is basically a proxy, it transfer request and events to its active child node
 * */
class AdaptableSizeNode(
    parentContext: NodeContext,
    var windowSizeInfoProvider: IWindowSizeInfoProvider
) : Node(parentContext) {

    private var navItems: MutableList<NavigatorNodeItem> = mutableListOf()
    private var startingPosition: Int = 0
    private var CompactNavigator: NavigatorNode? = null
    private var MediumNavigator: NavigatorNode? = null
    private var ExpandedNavigator: NavigatorNode? = null
    private var CurrentNavigatorNode =
        mutableStateOf<NavigatorNode?>(null) // todo: This should be a reactive state

    fun setNavItems(navItems: MutableList<NavigatorNodeItem>, startingPosition: Int) {
        this.navItems = navItems
        this.startingPosition = startingPosition
        CurrentNavigatorNode.value?.setNavItems(navItems, startingPosition)
    }

    fun setCompactNavigator(navigatorNode: NavigatorNode) {
        CompactNavigator = navigatorNode
    }

    fun setMediumNavigator(navigatorNode: NavigatorNode) {
        MediumNavigator = navigatorNode
    }

    fun setExpandedNavigator(navigatorNode: NavigatorNode) {
        ExpandedNavigator = navigatorNode
    }

    override fun start() {
        super.start()
        CurrentNavigatorNode.value?.getNode()?.start()
    }

    override fun stop() {
        super.stop()
        CurrentNavigatorNode.value?.getNode()?.stop()
    }

    // region: DeepLink

    override fun getDeepLinkNodes(): List<Node> {
        return listOfNotNull(
            CompactNavigator?.getNode(),
            MediumNavigator?.getNode(),
            ExpandedNavigator?.getNode()
        )
    }

    override fun onCheckChildMatchHandler(advancedPath: Path, matchingNode: Node): DeepLinkResult {
        val interceptingNode = CurrentNavigatorNode.value?.getNode() ?: matchingNode
        interceptingNode.context.subPath = matchingNode.context.subPath.copy()
        return interceptingNode.checkDeepLinkMatch(advancedPath)
    }

    override fun onNavigateChildMatchHandler(
        advancedPath: Path,
        matchingNode: Node
    ): DeepLinkResult {
        val interceptingNode = CurrentNavigatorNode.value?.getNode() ?: matchingNode
        interceptingNode.context.subPath = matchingNode.context.subPath.copy()
        onDeepLinkMatchingNode(interceptingNode)
        return interceptingNode.navigateUpToDeepLink(advancedPath)
    }

    override fun onDeepLinkMatchingNode(matchingNode: Node) {
        println("AdaptableWindowNode.onDeepLinkMatchingNode() matchingNode = ${matchingNode.context.subPath}")
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println("AdaptableWindowNode.Composing() lifecycleState = ${context.lifecycleState}")

        val windowSizeInfo by windowSizeInfoProvider.windowSizeInfo()

        CurrentNavigatorNode.value = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> {
                tryTransfer(CurrentNavigatorNode.value, CompactNavigator)
            }
            WindowSizeInfo.Medium -> {
                tryTransfer(CurrentNavigatorNode.value, MediumNavigator)
            }
            WindowSizeInfo.Expanded -> {
                tryTransfer(CurrentNavigatorNode.value, ExpandedNavigator)
            }
        }

        val CurrentNode = CurrentNavigatorNode.value?.getNode()

        if (CurrentNode != null) {
            CurrentNode.Content(modifier)
        } else {
            Box {
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

    private fun tryTransfer(
        donorNavigatorNode: NavigatorNode?,
        adoptingNavigatorNode: NavigatorNode?
    ): NavigatorNode? {

        if (adoptingNavigatorNode == donorNavigatorNode) {
            return adoptingNavigatorNode
        }

        val adoptingNavigatorCopy = adoptingNavigatorNode ?: return donorNavigatorNode

        return if (donorNavigatorNode == null) { // The first time when no node has been setup yet
            adoptingNavigatorNode.setNavItems(navItems, startingPosition)
            adoptingNavigatorNode.getNode().start()
            adoptingNavigatorNode
        } else { // do the real transfer here
            adoptingNavigatorCopy.transferFrom(donorNavigatorNode)
            donorNavigatorNode.getNode().stop()
            adoptingNavigatorNode.getNode().start()
            adoptingNavigatorNode
        }
    }

}