package com.macaosoftware.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.component.demo.viewmodel.BottomBarDemoViewModel
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.platform.AndroidBridge

class NavBarActivity : ComponentActivity() {

    val navBarComponent = NavBarComponent(
        navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
        // pushStrategy = FixSizedPushStrategy(1), // Uncomment to test other push strategies
        componentViewModel = BottomBarDemoViewModel(),
        content = NavBarComponentDefaults.NavBarComponentView
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AndroidComponentRender(
                    rootComponent = navBarComponent,
                    AndroidBridge(),
                    onBackPress = { finish() }
                )
            }
        }
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