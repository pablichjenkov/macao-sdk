package com.macaosoftware.app

import org.koin.core.module.Module

interface KoinModuleInitializer {
    suspend fun initialize() : Module
}