package com.pablichj.templato.component.core

internal fun ComponentWithChildren.destroyChildComponent() {
    if (getComponent().lifecycleState == ComponentLifecycleState.Started) {
        getComponent().dispatchStop()
        getComponent().dispatchDestroy()
    } else {
        getComponent().dispatchDestroy()
    }
}
