package com.pablichj.incubator.uistate3.node

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