package com.macaosoftware.app

sealed class StartupTaskStatus {
    class Running(val taskName: String) : StartupTaskStatus()
    class CompleteError(val error: InitializationError) : StartupTaskStatus()
    object CompleteSuccess : StartupTaskStatus()
}
