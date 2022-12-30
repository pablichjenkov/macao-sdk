package com.pablichj.encubator.node.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LaunchButton(
                        DrawerActivity::class.java,
                        "Drawer Example"
                    )
                    LaunchButton(
                        PagerActivity::class.java,
                        "Pager Example"
                    )
                    LaunchButton(
                        NavBarActivity::class.java,
                        "Bottom Bar Example"
                    )
                    LaunchButton(
                        FullAppIntroActivity::class.java,
                        "Full App Example"
                    )
                    LaunchButton(
                        HandleConfigChangesActivity::class.java,
                        "Handle onConfigurationChanged() Example"
                    )
                    LaunchButton(
                        AdaptableSizeNodeActivity::class.java,
                        "Swap Root Navigation Example"
                    )
                }
            }
        }

    }

    @Composable
    private fun LaunchButton(
        clazz: Class<out Activity>,
        text: String
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Button(
            onClick = { startActivity(clazz) }
        ) {
            Text(text)
        }
    }

    private fun startActivity(clazz: Class<out Activity>) {
        Intent(this, clazz).also {
            startActivity(it)
            finish()
        }
    }

}