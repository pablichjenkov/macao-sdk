package com.pablichj.incubator.uistate3.example.treebuilders

import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.example.DesktopAppNode
import com.pablichj.incubator.uistate3.node.navigation.SubPath

object DesktopAppTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var DesktopAppNode: DesktopAppNode

    fun build(): DesktopAppNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = null
        rootParentNodeContext.backPressedCallbackDelegate = object : BackPressedCallback() {
            override fun onBackPressed() {}
        }

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