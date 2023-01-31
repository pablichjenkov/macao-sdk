import com.pablichj.incubator.uistate3.BrowserViewportWindow
import com.pablichj.incubator.uistate3.example.helloWorld.HelloWorldApp
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        /*Window("Hello World") {
            HelloWorldApp()
        }*/
        BrowserViewportWindow("Hello World") {
            HelloWorldApp()
        }
    }
}

