package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import com.pablichj.incubator.uistate3.AndroidNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.DrawerStateTreeHolder
import com.pablichj.incubator.uistate3.node.Component

class DrawerActivity : ComponentActivity() {

    private val stateTreeHolder by viewModels<DrawerStateTreeHolder>()
    private lateinit var StateTree: Component

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is a NavigationDrawer
        StateTree = stateTreeHolder.getOrCreate()

        setContent {
            MaterialTheme {
                AndroidNodeRender(
                    rootComponent = StateTree,
                    onBackPressEvent = { finish() }
                )
            }
        }
    }

}