package com.pablichj.templato.component.demo.viewmodel

import androidx.compose.ui.graphics.Color
import com.pablichj.templato.component.core.viewmodel.ViewModel

class SimpleViewModel(
    val screenName: String,
    private val bgColor: Color,
    private val onNext: () -> Unit
) : ViewModel() {

    val text = "A sample text from the view Model"

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    fun next() {
        onNext()
    }

}
