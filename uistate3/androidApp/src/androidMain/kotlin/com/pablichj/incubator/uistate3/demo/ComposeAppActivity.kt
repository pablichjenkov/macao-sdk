package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.AndroidNodeRender
import example.nodes.SimpleNode

class ComposeAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AndroidNodeRender(
                    rootNode = SimpleNode("A Simple Node", Color.Cyan, {}),
                    onBackPressEvent = { finish() }
                )
            }
        }
    }
}
