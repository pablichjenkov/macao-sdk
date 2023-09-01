package com.macaosoftware.component.stack

import androidx.compose.runtime.MutableState

interface StackStatePresenter {
    var stackState: MutableState<StackState>
}

class StackStatePresenterDefault(
    override var stackState: MutableState<StackState>
) : StackStatePresenter