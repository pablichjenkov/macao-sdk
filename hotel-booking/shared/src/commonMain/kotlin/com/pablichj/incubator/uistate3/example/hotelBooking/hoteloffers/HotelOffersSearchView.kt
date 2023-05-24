package com.pablichj.incubator.uistate3.example.hotelBooking.hoteloffers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch

@Composable
internal fun HotelOffersSearchView(
    hotelListing: HotelListing,
    hotelOffers: List<HotelOfferSearch>,
    onHotelOffersRequest: (HotelOffersRequestData) -> Unit,
    onOfferSelected: (HotelOfferSearch.Offer) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        HotelOffersSearchTopForm(
            hotelListing = hotelListing,
            onHotelOffersRequest = {
                onHotelOffersRequest(it)
            }
        )
        if (hotelOffers.isNotEmpty()) {
            hotelOffers.forEach { hotelOffer ->
                Text("Results for ${hotelOffer.hotel.name}:")
                hotelOffer.offers.forEach { offer ->
                    HotelOfferView(
                        modifier = Modifier,
                        offer = offer,
                        onOfferClick = {
                            onOfferSelected(offer)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun HotelOffersSearchTopForm(
    hotelListing: HotelListing,
    onHotelOffersRequest: (HotelOffersRequestData) -> Unit
) {
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var checkingDate by remember { mutableStateOf("2023-12-31") }
    var numberOfAdultGuests by remember { mutableStateOf("1") }
    var numberOfRooms by remember { mutableStateOf("1") }
    var errorMessage by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(true) }

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp)
    ) {
        Text(
            text = "Fill reservation details",
            style = MaterialTheme.typography.h4
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Offers availability will be based on the data you input",
            style = MaterialTheme.typography.body1
        )
        Spacer(Modifier.height(24.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = hotelListing.name ?: "No Name Provided"
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = checkingDate,
            label = { Text("CheckingDate") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    //focus.moveFocus(FocusDirection.Next)
                }
            ),
            onValueChange = { checkingDate = it },
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberOfAdultGuests,
            label = { Text("How Many Adult Guest") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focus.clearFocus()
                    keyboardController?.hide()
                    //onSubmit()
                }
            ),
            onValueChange = { numberOfAdultGuests = it },
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberOfRooms,
            label = { Text("How Many Rooms") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focus.clearFocus()
                    keyboardController?.hide()
                    onHotelOffersRequest(
                        HotelOffersRequestData(
                            hotelListing.hotelId!!,
                            checkingDate,
                            numberOfAdultGuests,
                            numberOfRooms
                        )
                    )
                }
            ),
            onValueChange = { numberOfRooms = it },
            singleLine = true
        )
        Button(
            onClick = {
                onHotelOffersRequest(
                    HotelOffersRequestData(
                        hotelListing.hotelId!!,
                        checkingDate,
                        numberOfAdultGuests,
                        numberOfRooms
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            Text("Search")
        }
    }
}