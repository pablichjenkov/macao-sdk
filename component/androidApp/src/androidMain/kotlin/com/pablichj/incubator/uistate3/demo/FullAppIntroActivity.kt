package com.pablichj.incubator.uistate3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.pablichj.incubator.uistate3.AndroidComponentRender
import com.pablichj.incubator.uistate3.demo.treebuilders.FullAppIntroStateTreeHolder
import com.pablichj.incubator.uistate3.node.Component

class FullAppIntroActivity : ComponentActivity() {

    private val activityStateHolder by viewModels<FullAppIntroStateTreeHolder>()
    private lateinit var StateTree: Component

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is an AppCoordinator
        StateTree = activityStateHolder.getOrCreate()

        setContent {
            AndroidComponentRender(
                rootComponent = StateTree,
                onBackPressEvent = { finish() }
            )
        }
    }

}