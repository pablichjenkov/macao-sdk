package com.pablichj.encubator.node.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext

class DesktopAppNode(
    parentContext: NodeContext
) : Node(parentContext) {
    //val window1 = mutableStateListOf<WindowState>()
    val counter = mutableStateOf<Int>(0)
    val createdWindows = mutableListOf<WindowNode>()
    private val MainWindowNode = MainWindowNode(context) {
        openNewWindow()
    }

    init {
        //windows += MyWindowState("Initial window")

        //windows.add(MainWindowNode)
        //windows.add(SettingWindowNode)

    }

    fun openNewWindow() {
        createdWindows.add(SettingsWindowNode(context))
        updateScreen()
    }

    fun exit() {
        //windows.clear()
    }

    private fun updateScreen() {
        if (counter.value == Int.MAX_VALUE) {
            counter.value = 0
        } else {
            counter.value ++
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {

        if (counter.value >= 0) {

            MainWindowNode.Content(modifier)
            for (window in createdWindows) {
                key(window) {
                    window.Content(modifier)
                }
            }

        }

    }

}