package com.pablichj.incubator.uistate3.example.utils

import com.pablichj.incubator.uistate3.node.BackPressedCallback

object EmptyBackPressCallback: BackPressedCallback() {
    override fun onBackPressed() {
    }
}