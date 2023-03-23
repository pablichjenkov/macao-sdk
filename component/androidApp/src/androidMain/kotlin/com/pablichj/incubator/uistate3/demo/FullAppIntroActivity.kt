package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.incubator.uistate3.AndroidComponentRender
import com.pablichj.incubator.uistate3.demo.treebuilders.FullAppWithIntroTreeBuilder

class FullAppIntroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = FullAppWithIntroTreeBuilder.build()
        setContent {
            AndroidComponentRender(
                rootComponent = rootComponent,
                onBackPressEvent = { finish() }
            )
        }
    }

}