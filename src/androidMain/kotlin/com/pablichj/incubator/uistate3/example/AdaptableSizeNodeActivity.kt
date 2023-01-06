package com.pablichj.incubator.uistate3.example

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.example.treebuilders.AdaptableSizeStateTreeHolder
import com.pablichj.incubator.uistate3.node.*

class AdaptableSizeNodeActivity : ComponentActivity() {

    private val stateTreeHolder by viewModels<AdaptableSizeStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is an AdaptableWindow
        StateTree = stateTreeHolder.getOrCreate(
            windowSizeInfoProvider = AndroidWindowSizeInfoProvider(this),
        ).apply {
            context.rootNodeBackPressedDelegate = ForwardBackPressCallback { finish() }
        }

        setContent {
            MaterialTheme {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides AndroidBackPressDispatcher(
                        this@AdaptableSizeNodeActivity
                    )
                ) {
                    StateTree.Content(Modifier)
                }
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

}