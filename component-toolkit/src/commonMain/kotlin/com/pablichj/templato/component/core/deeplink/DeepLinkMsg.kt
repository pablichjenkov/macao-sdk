package com.pablichj.templato.component.core.deeplink

data class DeepLinkMsg (
    val path: List<String>,
    val resultListener: (DeepLinkResult) -> Unit,
    val componentConnection: ComponentConnection? = null
)