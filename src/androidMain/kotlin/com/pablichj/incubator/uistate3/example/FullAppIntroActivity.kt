package com.pablichj.incubator.uistate3.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.AndroidBackPressDispatcher
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.example.treebuilders.FullAppIntroStateTreeHolder

class FullAppIntroActivity : ComponentActivity() {

    private val activityStateHolder by viewModels<FullAppIntroStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is an AppCoordinator
        StateTree = activityStateHolder.getOrCreate(
            backPressDispatcher = AndroidBackPressDispatcher(this@FullAppIntroActivity),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {
                    finish()
                }
            }
        )
        setContent {
            MaterialTheme {
                StateTree.Content(Modifier)
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