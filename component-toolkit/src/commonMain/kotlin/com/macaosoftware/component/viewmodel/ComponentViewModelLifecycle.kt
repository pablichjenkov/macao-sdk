package com.macaosoftware.component.viewmodel

abstract class ComponentViewModelLifecycle {
    abstract fun onStart()
    abstract fun onStop()
    abstract fun onDestroy()
}
