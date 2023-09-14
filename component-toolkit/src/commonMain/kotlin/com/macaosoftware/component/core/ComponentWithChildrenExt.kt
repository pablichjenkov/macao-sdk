package com.macaosoftware.component.core

internal fun ComponentWithChildrenOneActive.destroyChildComponent() {
    if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
        getComponent().dispatchStop()
        getComponent().dispatchDestroy()
    } else {
        getComponent().dispatchDestroy()
    }
}
