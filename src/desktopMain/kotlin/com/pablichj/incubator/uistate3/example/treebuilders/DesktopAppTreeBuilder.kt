package com.pablichj.incubator.uistate3.example.treebuilders

import com.pablichj.incubator.uistate3.example.DesktopAppNode
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.navigation.SubPath

object DesktopAppTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var DesktopAppNode: DesktopAppNode

    fun build(): DesktopAppNode {

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