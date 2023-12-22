package com.macaosoftware.component.demo.viewmodel

import com.macaosoftware.component.demo.view.DemoType
import com.macaosoftware.component.viewmodel.ComponentViewModel

class MainScreenViewModel(
    private val onOptionSelected: (DemoType) -> Unit,
    private val onBackPress: () -> Boolean
) : ComponentViewModel() {

    override fun onAttach() {

    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDetach() {

    }

    override fun handleBackPressed(): Boolean {
        return this.onBackPress()
    }

    fun onClick(demoType: DemoType) = onOptionSelected(demoType)

}
