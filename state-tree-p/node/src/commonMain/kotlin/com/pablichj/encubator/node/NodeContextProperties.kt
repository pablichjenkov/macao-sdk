package com.pablichj.encubator.node

interface INavigationProvider {
    fun open()
    fun close()
}

interface IBackPressDispatcher {
    fun subscribe(backPressedCallback: BackPressedCallback)
    fun unsubscribe(backPressedCallback: BackPressedCallback)
}

abstract class BackPressedCallback {
    var onEnableChanged: ((Boolean) -> Unit)? = null
    abstract fun onBackPressed()
}