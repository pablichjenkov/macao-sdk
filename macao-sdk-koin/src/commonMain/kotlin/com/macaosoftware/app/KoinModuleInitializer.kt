package com.macaosoftware.app

import org.koin.core.module.Module

interface KoinModuleInitializer {
    fun initialize() : Module
}