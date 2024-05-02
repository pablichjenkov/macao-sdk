package com.macaosoftware.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.macaosoftware.app.MacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.StartupTaskRunnerDefault
import com.macaosoftware.component.demo.startup.DatabaseMigrationStartupTask
import com.macaosoftware.component.demo.startup.DemoRootComponentInitializer
import com.macaosoftware.component.demo.startup.LaunchDarklyStartupTask
import com.macaosoftware.component.demo.startup.SdkXyzStartupTask

class MainActivity : ComponentActivity() {

    val startupTasks = listOf(
        DatabaseMigrationStartupTask(),
        LaunchDarklyStartupTask(),
        SdkXyzStartupTask()
    )

    private val macaoApplicationState = MacaoApplicationState(
        pluginManagerInitializer = AndroidPluginManagerInitializer(this),
        startupTaskRunner = StartupTaskRunnerDefault(startupTasks),
        rootComponentInitializer = DemoRootComponentInitializer()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MacaoApplication(
                    applicationState = macaoApplicationState
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
