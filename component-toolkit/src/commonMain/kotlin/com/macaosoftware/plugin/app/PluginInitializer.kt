package com.macaosoftware.plugin.app

interface PluginInitializer {

    /**
     * This function should be executed in io/default dispatcher.
     * The purpose is initializing the basic components needed to run the App.
     * For example a DI container should be initialized here.
     * Things like Database Migration and LaunchDarkly initialization
     * are better handled in the StartupCoordinator Component.
     * */
    fun initialize(pluginManager: PluginManager)
}
