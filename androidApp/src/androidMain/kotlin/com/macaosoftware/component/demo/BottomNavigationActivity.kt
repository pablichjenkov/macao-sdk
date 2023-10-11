package com.macaosoftware.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.platform.AndroidBridge

class BottomNavigationActivity : ComponentActivity() {

    val bottomNavigationComponent = BottomNavigationComponent(
        // pushStrategy = FixSizedPushStrategy(1), // Uncomment to test other push strategies
        viewModelFactory = BottomNavigationDemoViewModelFactory(
            BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
        ),
        content = BottomNavigationComponentDefaults.BottomNavigationComponentView
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AndroidComponentRender(
                    rootComponent = bottomNavigationComponent,
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
