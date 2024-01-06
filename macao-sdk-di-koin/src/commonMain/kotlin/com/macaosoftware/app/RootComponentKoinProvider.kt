package com.macaosoftware.app

import com.macaosoftware.component.core.Component
import org.koin.core.component.KoinComponent
import kotlin.native.ObjCName

@ObjCName("RootComponentProvider")
interface RootComponentKoinProvider {
    suspend fun provideRootComponent(
        koinComponent: KoinComponent
    ): Component
}
