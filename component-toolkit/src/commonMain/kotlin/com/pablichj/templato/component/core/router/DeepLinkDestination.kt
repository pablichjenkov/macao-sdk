package com.pablichj.templato.component.core.router

import com.pablichj.templato.component.core.Component

class DeepLinkDestination(
    val deepLinkMatcher: ((String) -> Boolean),
    val component: Component
)