package com.macaosoftware.component.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
                        BottomNavigationActivity::class.java,
                        "BottomBar Example"
                    )
                    LaunchButton(
                        AdaptiveSizeActivity::class.java,
                        "Adaptive Navigation Example"
                    )
                    LaunchButton(
                        FullAppIntroActivity::class.java,
                        "Full App Navigation Example"
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