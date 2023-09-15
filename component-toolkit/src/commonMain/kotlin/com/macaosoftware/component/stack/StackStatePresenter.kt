package com.macaosoftware.component.stack

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

interface StackStatePresenter {
    var stackStateSnapshot: State<StackState>
}

class StackStatePresenterDefault(
    stackState: StackState = StackState()
) : StackStatePresenter {

    private var _stackStateSnapshot = mutableStateOf(stackState)
    override var stackStateSnapshot: State<StackState> = _stackStateSnapshot
}