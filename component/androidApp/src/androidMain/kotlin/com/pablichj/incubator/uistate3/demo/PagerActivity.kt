package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.incubator.uistate3.AndroidComponentRender
import com.pablichj.incubator.uistate3.demo.treebuilders.PagerTreeBuilder

class PagerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is a Pager
        val rootComponent = PagerTreeBuilder.build()
        setContent {
            AndroidComponentRender(
                rootComponent = rootComponent,
                onBackPressEvent = { finish() }
            )
        }
    }

}