package com.macaosoftware.component.core.deeplink

import com.macaosoftware.component.core.Component

data class DeepLinkMsg (
    val path: List<String>,
    val resultListener: (DeepLinkResult) -> Unit
)
