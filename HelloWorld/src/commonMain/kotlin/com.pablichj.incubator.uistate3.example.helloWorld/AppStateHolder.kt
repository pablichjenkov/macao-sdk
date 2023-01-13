package com.pablichj.incubator.uistate3.example.helloWorld

/**
 * This is a singleton object that will hold the ComposeAppState across the whole lifetime
 * of the Application process. Configuration changes does not affect UiState3 because UiState3
 * does not hold reference to anything in an Activity. Whatever is needed from an Activity it will
 * get it from the CompositionLocalProvider which refresh on configuration changes.
 * With UiState3, you can ignore Activity recreation when configuration changes happen.
 * The composable machinery will re run again updating the new view according to the
 * new orientation and size.
 * */
object AppStateHolder {
    val ComposeAppState by lazy {
        ComposeAppState()
    }
}