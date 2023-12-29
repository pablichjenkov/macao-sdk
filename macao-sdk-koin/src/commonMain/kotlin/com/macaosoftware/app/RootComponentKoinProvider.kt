package com.macaosoftware.app

import com.macaosoftware.component.core.Component
import org.koin.core.component.KoinComponent
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("RootComponentProvider")
interface RootComponentKoinProvider {
    suspend fun provideRootComponent(
        koinComponent: KoinComponent
    ): Component
}
