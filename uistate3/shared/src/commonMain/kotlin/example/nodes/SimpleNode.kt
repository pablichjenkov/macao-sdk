package example.nodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pablichj.incubator.uistate3.node.BackPressHandler
import com.pablichj.incubator.uistate3.node.Node

class SimpleNode(
    val text: String,
    val bgColor: Color,
    val onMessage: (Msg) -> Unit
) : Node() {

    sealed interface Msg {
        object Next : Msg
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("OnboardingStepNode::Composing()")
        BackPressHandler(
            node = this,
            onBackPressed = { this.backPressedCallbackHandler.onBackPressed() }
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
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = { onMessage(Msg.Next) }
            ) {
                Text(text = "Next")
            }
        }
    }

}