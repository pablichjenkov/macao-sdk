package com.pablichj.encubator.node.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.AndroidBackPressDispatcher
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.example.theme.AppTheme

class NavBarActivity : ComponentActivity() {

    lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {

        val inMemoryRotationPersister by viewModels<InMemoryRotationPersister>()

        StateTree = inMemoryRotationPersister.getOrCreateNode(
            backPressDispatcher = AndroidBackPressDispatcher(this@NavBarActivity),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {
                    finish()
                }
            }
        )

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
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