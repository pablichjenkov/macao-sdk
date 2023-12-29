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
import com.macaosoftware.app.MacaoKoinApplication
import com.macaosoftware.app.MacaoKoinApplicationState
import com.macaosoftware.app.RootComponentKoinProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.plugin.DemoKoinModuleInitializer
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent

class KoinActivity : ComponentActivity() {

    private val rootComponentKoinProvider = object : RootComponentKoinProvider {
        override suspend fun provideRootComponent(
            koinComponent: KoinComponent
        ): Component {

            delay(2000)

            return StackComponent<StackDemoViewModel>(
                viewModelFactory = StackDemoViewModelFactory(
                    stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                    onBackPress = {
                        this@KoinActivity.finish()
                        true
                    }
                ),
                content = StackComponentDefaults.DefaultStackComponentView
            )
        }

    }

    private val applicationState = MacaoKoinApplicationState(
        dispatcher = Dispatchers.IO,
        rootComponentKoinProvider = rootComponentKoinProvider,
        koinModuleInitializer = DemoKoinModuleInitializer()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MacaoKoinApplication(
                    onBackPress = { finish() },
                    applicationState = applicationState
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