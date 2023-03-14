package example.nodes

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
import com.pablichj.incubator.uistate3.node.backstack.BackPressHandler
import com.pablichj.incubator.uistate3.node.Component

class SimpleComponent(
    val text: String,
    val bgColor: Color,
    val onMessage: (Msg) -> Unit
) : Component() {

    override fun start() {
        super.start()
        println("${clazz}::start()")
    }

    override fun stop() {
        super.stop()
        println("${clazz}::stop()")
    }

    sealed interface Msg {
        object Next : Msg
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("$clazz::Composing()")
        BackPressHandler(
            component = this,
            onBackPressed = { this.backPressedCallbackDelegate.onBackPressed() }
        )
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