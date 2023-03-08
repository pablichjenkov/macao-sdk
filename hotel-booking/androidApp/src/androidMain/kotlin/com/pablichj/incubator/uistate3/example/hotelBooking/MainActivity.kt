package com.pablichj.incubator.uistate3.example.hotelBooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.incubator.uistate3.AndroidComponentRender
import example.nodes.AppCoordinatorComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                AppBuilder.buildGraph(),
                {}
            )
        }
    }
}