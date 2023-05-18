package com.pablichj.incubator.uistate3.example.hotelBooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.templato.component.core.AndroidComponentRender

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                rootComponent = AppBuilder.buildGraph(),
                onBackPressEvent = { finish() }
            )
        }
    }
}