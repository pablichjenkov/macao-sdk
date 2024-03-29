package com.macaosoftware.component.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.collectAsStateWithLifecycle
import com.macaosoftware.component.core.BackPressHandler
import com.macaosoftware.component.core.deeplink.DeepLinkMsg
import com.macaosoftware.component.core.deeplink.DeepLinkResult
import com.macaosoftware.component.core.deeplink.DefaultDeepLinkManager
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.component.core.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimpleRequestComponent(
    val screenName: String,
    val bgColor: Color
) : Component() {

    private var result by mutableStateOf("")
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var responseComponent by mutableStateOf<SimpleResponseComponent?>(null)

    override fun onActive() {
        println("${instanceId()}::onStart()")
    }

    override fun onInactive() {
        println("${instanceId()}::onStop()")
    }

    private fun subscribeToSimpleResponseComponent(component: SimpleResponseComponent?) {
        if (component == null) {
            println("SimpleResponseComponent not found in component tree")
            return
        }

        this@SimpleRequestComponent.responseComponent = component

        coroutineScope.launch {
            // Use collect if the exposed flow is a SharedFlow so it gets updated at any time
            /* responseComponent.resultSharedFlow.collect {
                result = it
            }*/

            // Use repeatOnLifecycle if the exposed flow is a StateFlow so it gets updated onStart
            repeatOnLifecycle {
                component.resultStateFlow.collect {
                    result = it
                }
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("${instanceId()}::Composing()")
        BackPressHandler()

        val rootComponent = LocalRootComponentProvider.current

        val result2 =
            responseComponent?.resultStateFlow?.collectAsStateWithLifecycle(this@SimpleRequestComponent)

        Column(
            modifier = modifier.fillMaxSize()
                .background(bgColor)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(vertical = 40.dp),
                onClick = {
                    DefaultDeepLinkManager().navigateToDeepLink(
                        rootComponent,
                        DeepLinkMsg(
                            /**
                             * The path is made of a list that consists of each uriFragment between
                             * the root component and the selected component. All uriFragments have to match
                             * in order to activate the complete path up to the component which the
                             * message is intended to.
                             * */
                            path = listOf("_root_navigator_stack", "_navigator_adaptive", "*", "Settings", "Page 3"),
                            resultListener = { result  ->
                                println("$screenName deeplink result: $result")
                                if (result is DeepLinkResult.Success) {
                                    subscribeToSimpleResponseComponent(
                                        result.componentOrNull<SimpleResponseComponent>()
                                    )
                                }
                            }
                        )
                    )
                }
            ) {
                Text(text = "Go To Settings/Page 3")
            }
            Spacer(modifier.padding(24.dp))
            if (result.isNotBlank()) {
                Text(
                    text = "Response Using repeatOnLifecycle(): ${result}",
                    fontSize = 20.sp
                )
            }
            if (result2 != null) {
                Text(
                    text = "Response Using collectAsStateWithLifecycle(): ${result2.value}",
                    fontSize = 20.sp
                )
            }
        }
    }

}
