package com.macaosoftware.app

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.component.core.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.dsl.koinApplication

class MacaoKoinApplicationState(
    dispatcher: CoroutineDispatcher,
    val rootComponentKoinProvider: RootComponentKoinProvider,
    private val koinModuleInitializer: KoinModuleInitializer
) {
    private val coroutineScope = CoroutineScope(dispatcher)

    var rootComponentState = mutableStateOf<Component?>(null)

    fun fetchRootComponent() {
        coroutineScope.launch {

            val appModule = koinModuleInitializer.initialize()

            val koinApplication = koinApplication {
                printLogger()
                modules(appModule)
            }

            val koinDiContainer = KoinDiContainer(koinApplication)

            rootComponentState.value =
                rootComponentKoinProvider.provideRootComponent(koinDiContainer)
        }
    }
}
