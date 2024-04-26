package com.macaosoftware.plugin.app

import com.macaosoftware.util.MacaoResult

interface Initializer<T> {

    /**
     * This function dictates whether the initialization will actually take place
     * or it will rely on the database cached values.
     * */
    fun shouldShowLoader(): Boolean

    /**
     * This function should be executed in io/default dispatcher.
     * The purpose is initializing the PluginManager with the basic
     * components needed to run the App.
     * Things like Database Migration and LaunchDarkly initialization
     * can also take place here.
     * */
    suspend fun initialize(): MacaoResult<T>
}

interface InitializerWithInput<I, T> {

    /**
     * This function dictates whether the initialization will actually take place
     * or it will rely on the database cached values.
     * */
    fun shouldShowLoader(): Boolean

    /**
     * This function should be executed in io/default dispatcher.
     * The purpose is initializing the PluginManager with the basic
     * components needed to run the App.
     * Things like Database Migration and LaunchDarkly initialization
     * can also take place here.
     * */
    suspend fun initialize(i: I): MacaoResult<T>
}
