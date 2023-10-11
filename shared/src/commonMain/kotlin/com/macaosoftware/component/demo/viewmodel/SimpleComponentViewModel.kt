package com.macaosoftware.component.demo.viewmodel

import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.viewmodel.ComponentViewModel

class SimpleComponentViewModel(
    val screenName: String,
    private val bgColor: Color,
    private val onNext: () -> Unit
) : ComponentViewModel() {

    val text = "A sample text from the view Model"

    override fun onAttach() {
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDetach() {

    }

    fun next() {
        onNext()
    }

}
