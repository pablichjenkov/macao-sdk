package com.macaosoftware.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.macaosoftware.app.AndroidMacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.RootComponentProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val rootComponentProvider = object : RootComponentProvider {
        override suspend fun provideRootComponent(): Component {

            delay(2000)

            return StackComponent<StackDemoViewModel>(
                viewModelFactory = StackDemoViewModelFactory(
                    stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                    onBackPress = {
                        this@MainActivity.finish()
                        true
                    }
                ),
                content = StackComponentDefaults.DefaultStackComponentView
            )
        }

    }

    private val macaoApplicationState = MacaoApplicationState(
        Dispatchers.IO,
        rootComponentProvider
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AndroidMacaoApplication(
                    onBackPress = { finish() },
                    macaoApplicationState = macaoApplicationState
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Example of Android Splash Screen"
                        )
                    }
                }
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