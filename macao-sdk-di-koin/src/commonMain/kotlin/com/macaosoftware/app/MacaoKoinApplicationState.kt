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
    private val koinRootModuleInitializer: KoinRootModuleInitializer
) {

    var stage = mutableStateOf<KoinAppStage>(KoinAppStage.Created)
    private val coroutineScope = CoroutineScope(dispatcher)

    fun start() {
        coroutineScope.launch {

            stage.value = KoinAppStage.Loading
            val appModule = koinRootModuleInitializer.initialize()

            val koinApplication = koinApplication {
                printLogger()
                modules(appModule)
            }

            val koinDiContainer = KoinDiContainer(koinApplication)

            val rootComponent = rootComponentKoinProvider.provideRootComponent(koinDiContainer)

            stage.value = KoinAppStage.Started(rootComponent)
        }
    }
}

sealed class KoinAppStage {
    data object Created : KoinAppStage()
    data object Loading : KoinAppStage()
    class Started(val rootComponent: Component) : KoinAppStage()
}
