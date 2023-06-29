package com.pablichj.templato.component.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DeepLinkDemoNode(
    val onDeepLinkClick: (destination: String) -> Unit,
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState(
        width = 300.dp, height = 800.dp
    )

    private val deepLinks = mutableListOf(
        "Home/Page1",
        "Orders/Page1",
        "Settings/Page1",
    )

    @Composable
    override fun WindowContent(modifier: Modifier) {

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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = destination,
                                    style = TextStyle(fontSize = 18.sp)
                                )
                            }

                        }
                    })
            }
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        //onWindowMinimized(activeNode, it)
                    }
                    .launchIn(this)
            }
        }

    }

}