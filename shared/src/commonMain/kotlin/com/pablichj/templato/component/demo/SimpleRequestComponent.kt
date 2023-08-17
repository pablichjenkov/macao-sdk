package com.pablichj.templato.component.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.consumeBackPressEvent
import com.pablichj.templato.component.core.deeplink.ComponentConnection
import com.pablichj.templato.component.core.deeplink.DeepLinkMsg
import com.pablichj.templato.component.core.deeplink.LocalRootComponentProvider

class SimpleRequestComponent(
    val screenName: String,
    val bgColor: Color
) : Component() {

    init {
        componentConnection = ComponentConnection()
    }

    override fun onStart() {
        println("${instanceId()}::onStart()")
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("${instanceId()}::Composing()")
        consumeBackPressEvent()

        val rootComponent = LocalRootComponentProvider.current

        Column(
            modifier = modifier.fillMaxSize()
                .background(bgColor)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(vertical = 40.dp),
                onClick = {
                    rootComponent?.navigateToDeepLink(
                        DeepLinkMsg(
                            path = listOf("_navigator_adaptive", "*", "Settings", "Page 3"),
                            resultListener = {
                                println("$screenName deeplink result: $it")
                            },
                            componentConnection = componentConnection?.apply {
                                request = "Request from $screenName"
                            }
                        )
                    )
                }
            ) {
                Text(text = "Go To Settings/Page 3")
            }
            Spacer(modifier.padding(24.dp))
            val request = componentConnection?.request
            if (request != null) {
                Text(
                    text = "Request: ${request}",
                    fontSize = 20.sp
                )
            }
            val response = componentConnection?.response
            if (response != null) {
                Spacer(Modifier.height(40.dp))
                Text(
                    text = "Response: ${response}",
                    fontSize = 20.sp
                )
            }
        }
    }

}
