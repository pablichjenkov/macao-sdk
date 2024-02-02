package com.macaosoftware.component.demo.plugin

import com.macaosoftware.app.KoinRootModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class DemoKoinRootModuleInitializer : KoinRootModuleInitializer {
    override suspend fun initialize(): Module {
        return module {
        }
    }
}
