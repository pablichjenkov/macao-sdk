package com.pablichj.incubator.uistate3.example.hotelBooking.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun PaymentForm(
    modifier: Modifier = Modifier,
    onSubmitBookingRequest: (PaymentFormState) -> Unit
) {
    val paymentFormState = remember { PaymentFormState() }
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        PayerPersonalInfo(modifier, paymentFormState)
        PayerCardInfo(modifier, paymentFormState)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSubmitBookingRequest(paymentFormState) }
        ) {
            Text("Submit Payment")
        }
    }
}

@Composable
internal fun PayerPersonalInfo(
    modifier: Modifier = Modifier,
    paymentFormState: PaymentFormState
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = firstName,
                label = { Text("Firstname") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        //focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = {
                    firstName = it
                    paymentFormState.firstName = it
                },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lastName,
                label = { Text("Lastname") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        //focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = {
                    lastName = it
                    paymentFormState.lastName = it
                },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phone,
                label = { Text("Phone") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        //focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = {
                    phone = it
                    paymentFormState.phone = it
                },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        //focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = {
                    email = it
                    paymentFormState.email = it
                },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
internal fun PayerCardInfo(
    modifier: Modifier = Modifier,
    paymentFormState: PaymentFormState
) {
    var cardBrand by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cardExpDate by remember { mutableStateOf("") }

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = cardBrand,
                label = { Text("Card Issuer") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        //focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = {
                    cardBrand = it
                    paymentFormState.cardBrand = it
                },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = cardNumber,
                label = { Text("Card Number") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        //focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = {
                    cardNumber = it
                    paymentFormState.cardNumber = cardNumber
                },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = cardExpDate,
                label = { Text("Expire Date") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        //focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = {
                    cardExpDate = it
                    paymentFormState.cardExpDate = it
                },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}
