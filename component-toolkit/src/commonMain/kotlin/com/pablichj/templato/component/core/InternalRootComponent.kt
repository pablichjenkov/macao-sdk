package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType
import com.pablichj.templato.component.core.router.DefaultRouter
import com.pablichj.templato.component.core.router.Router

internal class InternalRootComponent(
    private val platformRootComponent: Component,
    private val onBackPressEvent: (() -> Unit)? = null
) : Component(), ComponentTreeContext {

    init {
        platformRootComponent.setParent(this)
    }

    override fun handleBackPressed() {
        println("${instanceId()}::BackPressed event delegation reached the InternalRootComponent")
        onBackPressEvent?.invoke()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        // no-op
    }

    override fun getDeepLinkHandler(): DeepLinkMatchData {
        return DeepLinkMatchData(
            null,
            DeepLinkMatchType.MatchAny
        )
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return platformRootComponent
    }

    override val router: Router = DefaultRouter(this)

}