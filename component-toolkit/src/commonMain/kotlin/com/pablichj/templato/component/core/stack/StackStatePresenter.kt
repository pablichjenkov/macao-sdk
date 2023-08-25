package com.pablichj.templato.component.core.stack

import androidx.compose.runtime.MutableState

interface StackStatePresenter {
    var stackState: MutableState<StackState>
}

class StackStatePresenterDefault(
    override var stackState: MutableState<StackState>
) : StackStatePresenter