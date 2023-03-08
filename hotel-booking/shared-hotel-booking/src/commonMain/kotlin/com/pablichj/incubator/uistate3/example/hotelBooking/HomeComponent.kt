package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.topbar.TopBarComponent
import example.nodes.SimpleComponent

class HomeComponent : TopBarComponent() {


    val homeComponent = SimpleComponent(
        "Home Page",
        Color.Cyan
    ) {
        backStack.push(searchComponent)
    }

    val searchComponent = SimpleComponent(
        "Search Page",
        Color.Yellow
    ) {}


    override fun start() {
        //super.start()

        backStack.push(homeComponent)

    }

    /*@Composable
    internal fun Content(modifier: Modifier) {

    }*/

}