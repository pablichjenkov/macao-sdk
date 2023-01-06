package com.pablichj.incubator.uistate3.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.example.treebuilders.DrawerStateTreeHolder
import com.pablichj.incubator.uistate3.node.*

class DrawerActivity : ComponentActivity() {

    private val stateTreeHolder by viewModels<DrawerStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is a NavigationDrawer
        StateTree = stateTreeHolder.getOrCreate().apply {
            context.rootNodeBackPressedDelegate = ForwardBackPressCallback { finish() }
        }

        setContent {
            MaterialTheme {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides AndroidBackPressDispatcher(this@DrawerActivity),
                ) {
                    StateTree.Content(Modifier)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        StateTree.start()
    }

    override fun onStop() {
        super.onStop()
        StateTree.stop()
    }

}