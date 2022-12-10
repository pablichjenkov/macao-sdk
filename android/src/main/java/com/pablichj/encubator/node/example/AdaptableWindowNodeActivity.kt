package com.pablichj.encubator.node.example

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.AndroidBackPressDispatcher
import com.pablichj.encubator.node.AndroidWindowSizeInfoProvider
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.drawer.DrawerNode
import com.pablichj.encubator.node.example.builders.AdaptableWindowNodeActivityTreeBuilder
import com.pablichj.encubator.node.adaptable.AdaptableWindowNode
import com.pablichj.encubator.node.example.theme.AppTheme
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.panel.PanelNode

class AdaptableWindowNodeActivity : ComponentActivity() {

    private val AdaptableWindowNode: AdaptableWindowNode =
        AdaptableWindowNodeActivityTreeBuilder.build(
            windowSizeInfoProvider = AndroidWindowSizeInfoProvider(this),
            backPressDispatcher = AndroidBackPressDispatcher(this),
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
                AdaptableWindowNode.Content(Modifier)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        AdaptableWindowNode.start()
    }

    override fun onStop() {
        super.onStop()
        AdaptableWindowNode.stop()
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