package com.macaosoftware.component.core.deeplink

import com.macaosoftware.component.core.Component

sealed class DeepLinkResult {
    class Success(val component: Component) : DeepLinkResult() {
        inline fun <reified U : Component> componentOrNull(): U? {
            return component as? U
        }
    }

    data class Error(val errorMsg: String) : DeepLinkResult()
}
