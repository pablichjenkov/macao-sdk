package com.macaosoftware.component.core

internal fun ComponentWithChildrenOneActive.destroyChildComponent() {
    if (getComponent().lifecycleState == ComponentLifecycleState.Active) {
        getComponent().dispatchInactive()
        getComponent().dispatchDetach()
    } else {
        getComponent().dispatchDetach()
    }
}
