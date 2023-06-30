package com.pablichj.templato.component.core.router

interface Router {
    fun handleDeepLink(
        deepLinkMsg: DeepLinkMsg,
    )
}
