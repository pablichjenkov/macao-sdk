package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import com.pablichj.incubator.uistate3.AndroidNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.PagerStateTreeHolder
import com.pablichj.incubator.uistate3.node.Node

class PagerActivity : ComponentActivity() {

    private val activityStateHolder by viewModels<PagerStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is a Pager
        StateTree = activityStateHolder.getOrCreate()

        setContent {
            MaterialTheme {
                AndroidNodeRender(
                    rootNode = StateTree,
                    onBackPressEvent = { finish() }
                )
            }
        }
    }

}