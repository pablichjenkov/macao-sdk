package com.macaosoftware.component.core

interface ComponentWithChildrenManyActive {
    fun getComponent(): Component
    var childComponents: MutableList<Component>
    var activeComponentList: MutableList<Component>
    fun onDestroyChildComponent(component: Component)
}
