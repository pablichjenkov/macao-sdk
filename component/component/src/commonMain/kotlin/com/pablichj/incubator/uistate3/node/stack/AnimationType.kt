package com.pablichj.incubator.uistate3.node.stack

sealed class AnimationType {
    object Direct : AnimationType()
    object Reverse : AnimationType()
    object Enter : AnimationType()
    object Exit : AnimationType()
}