package com.pablichj.templato.component.core.router

data class DeepLinkMsg (
    val path: List<String>,
    val resultListener: (DeepLinkResult) -> Unit
)