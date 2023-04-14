package com.pablichj.incubator.amadeus.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.incubator.amadeus.storage.DriverFactory
import com.pablichj.incubator.amadeus.storage.createDatabase
import com.pablichj.templato.component.core.AndroidComponentRender

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = createDatabase(DriverFactory(this))

        setContent {
            AndroidComponentRender(
                rootComponent = AmadeusDemoComponent(database),
                onBackPressEvent = { finishAffinity() }
            )
        }
    }
}