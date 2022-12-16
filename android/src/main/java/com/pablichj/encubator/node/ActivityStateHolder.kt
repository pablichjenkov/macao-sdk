package com.pablichj.encubator.node

import androidx.lifecycle.ViewModel

abstract class ActivityStateHolder<T : Node> : ViewModel() {
    abstract fun getOrCreateStateTree(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): T
}