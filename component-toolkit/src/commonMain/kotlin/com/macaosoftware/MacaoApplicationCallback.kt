package com.macaosoftware

import com.macaosoftware.plugin.MacaoPlugin

interface MacaoApplicationCallback : MacaoPlugin {
    fun onExit()
}