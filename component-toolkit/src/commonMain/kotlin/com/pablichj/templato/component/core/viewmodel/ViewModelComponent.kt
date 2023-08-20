package com.pablichj.templato.component.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component

open class ViewModelComponent<VM : ViewModel>(
    private var viewModel: VM,
    private val content: @Composable (VM) -> Unit
) : Component() {

    override fun onStart() {
        viewModel.dispatchStart()
    }

    override fun onStop() {
        viewModel.dispatchStop()
    }

    override fun onDestroy() {
        viewModel.dispatchDestroy()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        content(viewModel)
    }

}
