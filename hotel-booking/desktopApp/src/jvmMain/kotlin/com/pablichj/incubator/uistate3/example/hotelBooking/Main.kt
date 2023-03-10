package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.pablichj.incubator.uistate3.DesktopComponentRender
import example.nodes.AppCoordinatorComponent

fun main() =
    singleWindowApplication(
        title = "Hotel Booking",
        state = WindowState(size = DpSize(500.dp, 800.dp))
    ) {

        val rootComponent = AppBuilder.buildGraph()

        DesktopComponentRender(
            rootComponent,
            {}
        )
    }
