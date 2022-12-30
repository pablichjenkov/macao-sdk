package com.pablichj.encubator.node.example

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.AndroidBackPressDispatcher
import com.pablichj.encubator.node.AndroidWindowSizeInfoProvider
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.example.statetrees.AdaptableSizeStateTreeHolder

class AdaptableSizeNodeActivity : ComponentActivity() {

    private val stateTreeHolder by viewModels<AdaptableSizeStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is an AdaptableWindow
        StateTree = stateTreeHolder.getOrCreate(
            windowSizeInfoProvider = AndroidWindowSizeInfoProvider(this),
            backPressDispatcher = AndroidBackPressDispatcher(this),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {
                    finish()
                }
            }
        )
        setContent {
            MaterialTheme {
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