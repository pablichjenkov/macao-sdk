package com.pablichj.incubator.uistate3.example.hotelBooking.hoteloffers

import QueryParam
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
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.endpoint.accesstoken.ResolveAccessTokenUseCaseSource
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.MultiHotelOffersRequest
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.MultiHotelOffersUseCase
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch
import com.pablichj.incubator.uistate3.example.hotelBooking.InMemoryAccessTokenDao
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.consumeBackPressEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelOffersComponent(
    private val hotelListing: HotelListing,
    private val onOfferSelected: (HotelOfferSearch.Offer) -> Unit
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val accessTokenDao = InMemoryAccessTokenDao()

    private suspend fun getMultiHotelsOffers(
        hotelOffersRequestData: HotelOffersRequestData
    ): List<HotelOfferSearch> {
        val accessToken = ResolveAccessTokenUseCaseSource(
            Dispatchers,
            accessTokenDao
        ).doWork()

        if (accessToken == null) {
            println("No saved token")
            return emptyList()
        } else {
            println("Using saved token: ${accessToken.accessToken}")
        }

        val callResult = MultiHotelOffersUseCase(
            Dispatchers
        ).doWork(
            MultiHotelOffersRequest(
                accessToken,
                listOf(
                    QueryParam.HotelIds(hotelOffersRequestData.hotelId),
                    QueryParam.Adults(hotelOffersRequestData.numberOfAdults),
                    QueryParam.CheckInDate(hotelOffersRequestData.checkingDate),
                    QueryParam.RoomQuantity(hotelOffersRequestData.roomQuantity),
                    QueryParam.BestRateOnly("false")
                )
            )
        )

        return when (callResult) {
            is CallResult.Error -> {
                println("Error in multi hotel offers: ${callResult.error}")
                emptyList()
            }

            is CallResult.Success -> {
                callResult.responseBody.data
            }
        }
    }

    private fun doHotelOffersSearch(hotelOffersRequestData: HotelOffersRequestData) {
        coroutineScope.launch {
            val token = accessTokenDao.lastOrNull() ?: run {
                // getAccessToken()
                println("Token not found, start the flow again to get 1 token in the first step")
            }
            // todo: pass the access token in each request
            hotelOffers = getMultiHotelsOffers(hotelOffersRequestData)
        }
    }

    private var hotelOffers by mutableStateOf<List<HotelOfferSearch>>(emptyList())

    @Composable
    override fun Content(modifier: Modifier) {
        println("HotelOffersComponent::Composing()")
        consumeBackPressEvent()
        HotelOffersSearchView(
            hotelListing = hotelListing,
            hotelOffers = hotelOffers,
            onHotelOffersRequest = { doHotelOffersSearch(it) },
            onOfferSelected = { onOfferSelected(it) }
        )
    }

}
