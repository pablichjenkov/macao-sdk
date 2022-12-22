package com.pablichj.encubator.node.example.statetrees

import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.IBackPressDispatcher
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.example.DesktopAppNode
import com.pablichj.encubator.node.navigation.SubPath

object DesktopAppTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var DesktopAppNode: DesktopAppNode

    fun getOrCreateDesktopAppNode(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): DesktopAppNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (DesktopAppTreeBuilder::DesktopAppNode.isInitialized) {
            return DesktopAppNode.apply {
                this.context.parentContext = rootParentNodeContext
            }
        }

        return DesktopAppNode(
            rootParentNodeContext
        ).also {
            it.context.subPath = SubPath("DesktopAppNode")
            DesktopAppNode = it
        }

    }

}