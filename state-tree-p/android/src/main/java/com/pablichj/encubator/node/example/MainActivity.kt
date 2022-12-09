package com.pablichj.encubator.node.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.encubator.node.example.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
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
                        FullAppActivity::class.java,
                        "Full App Example"
                    )
                    LaunchButton(
                        HandleConfigChangesActivity::class.java,
                        "Handle onConfigurationChanged() Example"
                    )
                    LaunchButton(
                        AdaptableWindowNodeActivity::class.java,
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

    // TODO: Implement FloatingButton navigation node: FloatingButtonNode
    @Composable
    fun BoxScope.FloatingButton() {
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                //(StateTree as DrawerNode).popToHome()
            }
        ) {
            Text(text = "Pop Root")
        }
    }

}