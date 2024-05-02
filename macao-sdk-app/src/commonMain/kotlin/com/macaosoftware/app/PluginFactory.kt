package com.macaosoftware.app

import com.macaosoftware.plugin.MacaoPlugin

interface PluginFactory<P : MacaoPlugin> {

    fun getPlugin(): P?
}
