package com.macaosoftware.component.core

import androidx.compose.runtime.MutableState

interface ComponentWithChildrenOneActive {
    fun getComponent(): Component
    var childComponents: MutableList<Component>
    var activeComponent: MutableState<Component?>
    fun onDetachChildComponent(component: Component)
}
