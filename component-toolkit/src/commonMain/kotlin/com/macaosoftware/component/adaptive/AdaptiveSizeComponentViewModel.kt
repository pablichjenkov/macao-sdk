package com.macaosoftware.component.adaptive

import com.macaosoftware.component.viewmodel.ComponentViewModel

abstract class AdaptiveSizeComponentViewModel : ComponentViewModel() {

    abstract fun onCreate(adaptiveSizeComponent: AdaptiveSizeComponent)
}

class AdaptiveSizeComponentDefaultViewModel : AdaptiveSizeComponentViewModel() {

    override fun onCreate(adaptiveSizeComponent: AdaptiveSizeComponent) {
        println("AdaptiveSizeComponentDefaultViewModel::create()")
    }

    override fun onStart() {
        println("AdaptiveSizeComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("AdaptiveSizeComponentDefaultViewModel::onStop()")
    }

    override fun onDestroy() {
        println("AdaptiveSizeComponentDefaultViewModel::onDestroy()")
    }
}
