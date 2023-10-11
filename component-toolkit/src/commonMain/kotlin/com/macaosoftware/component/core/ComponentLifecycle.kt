package com.macaosoftware.component.core

abstract class ComponentLifecycle {
    protected abstract fun onAttach()
    protected abstract fun onStart()
    protected abstract fun onStop()
    protected abstract fun onDetach()
}
