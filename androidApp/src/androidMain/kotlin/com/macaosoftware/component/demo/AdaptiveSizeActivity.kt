package com.macaosoftware.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.viewmodel.AdaptiveSizeDemoViewModel
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.platform.AndroidBridge

class AdaptiveSizeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adaptiveSizeComponent = AdaptiveSizeComponent(AdaptiveSizeDemoViewModel())
        setContent {
            MaterialTheme {
                AndroidComponentRender(
                    rootComponent = adaptiveSizeComponent,
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

@Preview
@Composable
fun DrawerPreview() {

    val navbarItems = listOf(
        NavItem(
            component = SimpleComponent(
                "Tab 1",
                Color.Magenta,
            ) {},
            label = "Tab 1",
            icon = Icons.Default.Email
        ),
        NavItem(
            component = SimpleComponent(
                "Tab 2",
                Color.Blue,
            ) {},
            label = "Tab 2",
            icon = Icons.Default.AccountBox,

            )
    )

    val navBarComponent = NavBarComponent(
        navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
        componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
        content = NavBarComponentDefaults.NavBarComponentView
    ).also {
        it.setNavItems(navItems = navbarItems, selectedIndex = 0)
    }

    MaterialTheme {
        AndroidComponentRender(
            rootComponent = navBarComponent,
            AndroidBridge(),
            onBackPress = { }
        )
    }

}
