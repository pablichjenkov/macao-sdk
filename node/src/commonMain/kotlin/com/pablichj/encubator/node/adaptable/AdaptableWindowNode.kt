package com.pablichj.encubator.node.adaptable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.encubator.node.*

class AdaptableWindowNode(
    parentContext: NodeContext,
    var windowSizeInfoProvider: IWindowSizeInfoProvider
) : Node(parentContext) {

    private var navItems: MutableList<NavigatorNodeItem> = mutableListOf()
    private var startingPosition :Int = 0
    private var CompactNavigator: NavigatorNode? = null
    private var MediumNavigator: NavigatorNode? = null
    private var ExpandedNavigator: NavigatorNode? = null
    private var CurrentNavigatorNode: NavigatorNode? = null

    fun setNavItems(navItems: MutableList<NavigatorNodeItem>, startingPosition: Int) {
        this.navItems = navItems
        this.startingPosition = startingPosition
        CurrentNavigatorNode?.setNavItems(navItems, startingPosition)
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
        CurrentNavigatorNode?.getNode()?.start()
    }

    override fun stop() {
        super.stop()
        CurrentNavigatorNode?.getNode()?.stop()
    }

    @Composable
    override fun Content(modifier: Modifier) {

        val windowSizeInfo by windowSizeInfoProvider.windowSizeInfo()

        CurrentNavigatorNode = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> {
                tryTransfer(CurrentNavigatorNode, CompactNavigator)
            }
            WindowSizeInfo.Medium -> {
                tryTransfer(CurrentNavigatorNode, MediumNavigator)
            }
            WindowSizeInfo.Expanded -> {
                tryTransfer(CurrentNavigatorNode, ExpandedNavigator)
            }
        }

        val CurrentNode = CurrentNavigatorNode?.getNode()

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