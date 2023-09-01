package com.macaosoftware.component.stack

sealed class AnimationType {
    object Direct : AnimationType()
    object Reverse : AnimationType()
    object Enter : AnimationType()
    object Exit : AnimationType()
}