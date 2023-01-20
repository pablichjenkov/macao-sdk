package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.demo.treebuilders.PagerStateTreeHolder
import com.pablichj.incubator.uistate3.node.AndroidBackPressDispatcher
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node

class PagerActivity : ComponentActivity() {

    private val activityStateHolder by viewModels<PagerStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is a Pager
        StateTree = activityStateHolder.getOrCreate().apply {
            context.rootNodeBackPressedDelegate = ForwardBackPressCallback { finish() }
        }

        setContent {
            MaterialTheme {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides AndroidBackPressDispatcher(
                        this@PagerActivity
                    ),
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