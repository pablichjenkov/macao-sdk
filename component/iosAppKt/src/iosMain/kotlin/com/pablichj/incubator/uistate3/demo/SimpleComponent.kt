package com.pablichj.incubator.uistate3.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.consumeBackPressEvent

class SimpleComponent(
    val text: String,
    val bgColor: Color,
    val onMessage: (Msg) -> Unit
) : Component() {

    override fun start() {
        super.start()
        println("SimpleComponent::start()")
    }

    override fun stop() {
        super.stop()
        println("SimpleComponent::stop()")
    }

    sealed interface Msg {
        object Next : Msg
    }

    @Composable
    internal fun Content(modifier: Modifier) {
        println("CustomTopBarComponent::Composing()")
        consumeBackPressEvent()
        Box(modifier = modifier.fillMaxSize().background(bgColor)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 40.dp),
                onClick = { onMessage(Msg.Next) }
            ) {
                Text(text = "Next")
            }
        }
    }

}