package com.macaosoftware.component.core

internal fun ComponentWithChildrenOneActive.destroyChildComponent() {
    if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
        getComponent().dispatchStop()
        getComponent().dispatchDetach()
    } else {
        getComponent().dispatchDetach()
    }
}
