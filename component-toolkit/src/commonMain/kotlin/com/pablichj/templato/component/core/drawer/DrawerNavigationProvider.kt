package com.pablichj.templato.component.core.drawer

interface DrawerNavigationProvider {
    fun open()
    fun close()
}

internal class EmptyDrawerNavigationProvider: DrawerNavigationProvider {
    override fun open() {
        // no-op
    }
    override fun close() {
        // no-op
    }
}