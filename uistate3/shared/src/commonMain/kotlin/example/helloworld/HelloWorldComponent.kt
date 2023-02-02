package example.helloworld

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.backstack.BackPressHandler
import com.pablichj.incubator.uistate3.node.Component

class HelloWorldComponent : Component() {

    private val helloWorldState = HelloWorldState()

    @Composable
    override fun Content(modifier: Modifier) {
        BackPressHandler(
            component = this,
            onBackPressed = { backPressedCallbackDelegate.onBackPressed() }
        )
        HelloWorldView(helloWorldState)
    }

}