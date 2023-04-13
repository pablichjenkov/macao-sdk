package com.pablichj.incubator.uistate3.node.router

import com.pablichj.incubator.uistate3.node.Component

class DeepLinkDestination(
    val deepLinkMatcher: ((String) -> Boolean),
    val component: Component
)