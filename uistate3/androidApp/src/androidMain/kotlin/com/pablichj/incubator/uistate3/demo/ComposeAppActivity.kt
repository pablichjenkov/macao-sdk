package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.AndroidNodeRender
import example.nodes.SimpleComponent

class ComposeAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AndroidNodeRender(
                    rootComponent = SimpleComponent("A Simple Node", Color.Cyan, {}),
                    onBackPressEvent = { finish() }
                )
            }
        }
    }
}
