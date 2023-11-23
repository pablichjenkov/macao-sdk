package com.macaosoftware.plugin

interface ImageLoaderPlugin {
    fun loadImage(url: String)
    fun loadFromDisk()
}