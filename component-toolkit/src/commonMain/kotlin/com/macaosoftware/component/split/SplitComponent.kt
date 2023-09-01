package com.macaosoftware.component.split

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.macaosoftware.component.core.Component

class SplitComponent(
    private val config: Config = DefaultConfig
) : Component() {
    private var topComponent: Component? = null
    private var bottomComponent: Component? = null

    fun setTopComponent(topComponent: Component) {
        this.topComponent = topComponent.apply {
            setParent(this@SplitComponent)
        }
    }

    fun setBottomComponent(bottomComponent: Component) {
        this.bottomComponent = bottomComponent.apply {
            setParent(this@SplitComponent)
        }
    }

    override fun onStart() {
        println("${instanceId()}::start")
        topComponent?.dispatchStart()
        bottomComponent?.dispatchStart()
    }

    override fun onStop() {
        println("${instanceId()}::stop")
        topComponent?.dispatchStop()
        bottomComponent?.dispatchStop()
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return if (topComponent?.uriFragment == nextUriFragment) {
            topComponent
        } else if (bottomComponent?.uriFragment == nextUriFragment) {
            bottomComponent
        } else null
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("${instanceId()}::Composing()")
        Column(modifier = Modifier.fillMaxSize()) {
            val topComponentCopy = topComponent
            if (topComponentCopy != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5F)
                        .padding(start = 40.dp, top = 40.dp, end = 40.dp, bottom = 20.dp)
                ) {
                    topComponentCopy.Content(Modifier)
                }
            }

            val bottomComponentCopy = bottomComponent
            if (bottomComponentCopy != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 40.dp, top = 20.dp, end = 40.dp, bottom = 40.dp)
                ) {
                    bottomComponentCopy.Content(Modifier)
                }
            }
        }
    }

    class Config(
        var splitStyle: SplitStyle = SplitStyle()
    )

    companion object {
        val DefaultConfig = Config(
            SplitStyle()
        )
    }

}