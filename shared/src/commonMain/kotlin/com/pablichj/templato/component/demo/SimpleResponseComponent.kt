package com.pablichj.templato.component.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.BackPressHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SimpleResponseComponent(
    val screenName: String,
    val bgColor: Color
) : Component() {

    private val _resultSharedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val resultSharedFlow: SharedFlow<String> = _resultSharedFlow.asSharedFlow()

    private val _resultStateFlow = MutableStateFlow<String>("")
    val resultStateFlow: StateFlow<String> = _resultStateFlow.asStateFlow()

    override fun onStart() {
        println("${instanceId()}::onStart()")
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("${instanceId()}::Composing()")
        BackPressHandler()

        var response by remember(this@SimpleResponseComponent) {
            mutableStateOf("")
        }

        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = modifier.fillMaxSize()
                .background(bgColor)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(vertical = 40.dp),
                onClick = {
                    _resultStateFlow.update { response }
                    coroutineScope.launch { _resultSharedFlow.emit(response) }
                    handleBackPressed()
                }
            ) {
                Text(text = "Set Result")
            }
            Spacer(modifier.padding(24.dp))
            Text(
                text = "Enter result bellow:",
                fontSize = 24.sp
            )
            Spacer(modifier.padding(40.dp))
            TextField(
                value = response,
                onValueChange = {
                    response = it
                },
                textStyle = TextStyle(fontSize = 24.sp)
            )

        }
    }

}
