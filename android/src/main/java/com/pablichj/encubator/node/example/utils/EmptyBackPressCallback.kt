package com.pablichj.encubator.node.example.utils

import com.pablichj.encubator.node.BackPressedCallback

object EmptyBackPressCallback: BackPressedCallback() {
    override fun onBackPressed() {
    }
}