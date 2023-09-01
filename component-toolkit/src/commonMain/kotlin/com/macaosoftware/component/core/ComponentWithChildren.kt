package com.macaosoftware.component.core

import androidx.compose.runtime.MutableState

interface ComponentWithChildren {
    fun getComponent(): Component
    var childComponents: MutableList<Component>
    var activeComponent: MutableState<Component?>
    fun onDestroyChildComponent(component: Component)
}
