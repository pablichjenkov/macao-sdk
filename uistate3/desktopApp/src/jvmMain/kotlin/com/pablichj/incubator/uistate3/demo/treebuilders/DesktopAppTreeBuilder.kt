package com.pablichj.incubator.uistate3.demo.treebuilders

import com.pablichj.incubator.uistate3.demo.DesktopAppNode

object DesktopAppTreeBuilder {

    private lateinit var DesktopAppNode: DesktopAppNode

    fun build(): DesktopAppNode {

        if (DesktopAppTreeBuilder::DesktopAppNode.isInitialized) {
            return DesktopAppNode
        }

        return DesktopAppNode().also {
            //it.context.subPath = SubPath("DesktopAppNode")
            DesktopAppNode = it
        }

    }

}