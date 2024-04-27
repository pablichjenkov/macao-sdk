package com.macaosoftware.plugin.app

sealed class StartupTaskStatus {
    class Running(val taskName: String) : StartupTaskStatus()
    class CompleteError(val errorMsg: String) : StartupTaskStatus()
    object CompleteSuccess : StartupTaskStatus()
}
