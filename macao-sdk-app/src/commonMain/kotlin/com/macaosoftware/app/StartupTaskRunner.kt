package com.macaosoftware.app

import kotlinx.coroutines.flow.Flow

interface StartupTaskRunner {

    /**
     * Implementation will return a flow updating the status of each task that runs
     * in the Application startup flow.
     * */
    fun initialize(pluginManager: PluginManager): Flow<StartupTaskStatus>
}
