import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.pablichj.incubator.uistate3.FloatingButton
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import kotlin.system.exitProcess

fun main() =
    singleWindowApplication(
        title = "Hello World",
        state = WindowState(size = DpSize(500.dp, 800.dp))
    ) {
        val jvmBackPressDispatcher = remember {
            DefaultBackPressDispatcher()
        }

        CompositionLocalProvider(
            LocalBackPressedDispatcher provides jvmBackPressDispatcher,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                ComposeApp(
                    onExit = {
                        exitProcess(0)
                    }
                )
                FloatingButton(
                    modifier = Modifier.offset(y = 48.dp),
                    alignment = Alignment.TopStart,
                    onClick = { jvmBackPressDispatcher.dispatchBackPressed() }
                )
            }
        }
    }