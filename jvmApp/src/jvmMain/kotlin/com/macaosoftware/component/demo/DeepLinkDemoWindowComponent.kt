package com.macaosoftware.component.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.core.Component

class DeepLinkDemoWindowComponent(
    val onDeepLinkClick: (destination: List<String>) -> Unit,
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState(
        width = 300.dp, height = 800.dp
    )

    private val deepLinks = mutableListOf(
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Home", "Page 1"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Home", "Page 2"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Home", "Page 3"),

        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_1", "Page 1"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_1", "Page 2"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_1", "Page 3"),

        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_2", "Page 1"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_2", "Page 2"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_2", "Page 3"),

        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_3", "Page 1"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_3", "Page 2"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Orders", "Tab_3", "Page 3"),

        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Settings", "Page 1"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Settings", "Page 2"),
        listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Settings", "Page 3"),
    )

    @Composable
    override fun Content(modifier: Modifier) {

        Window(
            state = windowState,
            onCloseRequest = {
                onCloseClick()
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = deepLinks,
                    itemContent = { destination ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    onDeepLinkClick(destination)
                                },
                            elevation = 8.dp
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = destination.joinToString("/"),
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }
                    })
            }
        }
    }

}
