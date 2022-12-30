package com.pablichj.encubator.node.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.AndroidBackPressDispatcher
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.example.statetrees.DrawerStateTreeHolder

class DrawerActivity : ComponentActivity() {

    private val stateTreeHolder by viewModels<DrawerStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is a NavigationDrawer
        StateTree = stateTreeHolder.getOrCreate(
            backPressDispatcher = AndroidBackPressDispatcher(this@DrawerActivity),
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