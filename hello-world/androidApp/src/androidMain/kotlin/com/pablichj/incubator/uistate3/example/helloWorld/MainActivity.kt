package com.pablichj.incubator.uistate3.example.helloWorld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.incubator.uistate3.AndroidComponentRender

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                rootComponent = HelloWorldComponent(),
                onBackPressEvent = { finishAffinity() }
            )
        }
    }
}