package com.macaosoftware.component.core.deeplink

data class DeepLinkMsg (
    val path: List<String>,
    val resultListener: (DeepLinkResult) -> Unit
)
