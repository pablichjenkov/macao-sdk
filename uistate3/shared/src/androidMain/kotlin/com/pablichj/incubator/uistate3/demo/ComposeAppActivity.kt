package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.pablichj.incubator.uistate3.ComposeApp
import com.pablichj.incubator.uistate3.node.AndroidBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher

class ComposeAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides AndroidBackPressDispatcher(this@ComposeAppActivity),
            ) {
                ComposeApp(
                    onExit = { finish() }
                )
            }
        }
    }
}
