package example.helloworld

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.BackPressHandler
import com.pablichj.incubator.uistate3.node.Node

class HelloWorldNode : Node() {

    private val helloWorldState = HelloWorldState()

    @Composable
    override fun Content(modifier: Modifier) {
        BackPressHandler(
            node = this,
            onBackPressed = { backPressedCallbackHandler.onBackPressed() }
        )
        HelloWorldView(helloWorldState)
    }

}