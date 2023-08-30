package com.pablichj.templato.component.core.deeplink

import com.pablichj.templato.component.core.Component

data class DeepLinkMsg (
    val path: List<String>,
    val resultListener: (DeepLinkResult) -> Unit
)
