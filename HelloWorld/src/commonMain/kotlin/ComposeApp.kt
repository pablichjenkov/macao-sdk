import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.timestampMs
import kotlinx.coroutines.delay

@Composable
fun ComposeApp(
    onExit: () -> Unit = {}
) {

    val timeText = remember {
        mutableStateOf("")
    }

    val DrawerNode = remember {
        DrawerTreeBuilder.build(onExit).also { it.start() }
    }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DrawerNode.Content(Modifier)
            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(start = 56.dp, top = 56.dp, end = 16.dp),
                text = timeText.value
            )
        }
    }

    LaunchedEffect(Unit) {
        var timeMs = timestampMs()
        while (true) {
            timeText.value = "Time Elapsed: $timeMs milliSec"
            delay(1000)
            timeMs = timestampMs()
        }
    }

}