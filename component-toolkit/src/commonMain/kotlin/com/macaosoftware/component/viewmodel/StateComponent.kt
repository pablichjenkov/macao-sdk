package com.macaosoftware.component.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component

class StateComponent<State>(
    private val componentViewModel: StateViewModel<State>,
    private val content: @Composable StateComponent<State>.(
        modifier: Modifier,
        componentViewModel: StateViewModel<State>
    ) -> Unit
) : Component() {
    @Composable
    override fun Content(modifier: Modifier) {
        this@StateComponent.content(modifier, componentViewModel)
    }
}

abstract class StateViewModel<U> : ComponentViewModel()
