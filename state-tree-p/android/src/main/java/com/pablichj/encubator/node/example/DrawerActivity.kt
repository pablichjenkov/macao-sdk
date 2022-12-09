package com.pablichj.encubator.node.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.AndroidBackPressDispatcher
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.example.builders.DrawerActivityTreeBuilder
import com.pablichj.encubator.node.example.theme.AppTheme

class DrawerActivity : ComponentActivity() {

    private val StateTree: Node = DrawerActivityTreeBuilder.build(
        backPressDispatcher = AndroidBackPressDispatcher(this@DrawerActivity),
        backPressedCallback = object : BackPressedCallback() {
            override fun onBackPressed() {
                finish()
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
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