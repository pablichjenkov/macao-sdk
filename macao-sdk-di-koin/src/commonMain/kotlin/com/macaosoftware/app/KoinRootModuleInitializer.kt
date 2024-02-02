package com.macaosoftware.app

import org.koin.core.module.Module

interface KoinRootModuleInitializer {
    suspend fun initialize() : Module
}