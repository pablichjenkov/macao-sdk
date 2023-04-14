package com.pablichj.incubator.amadeus.demo

import QueryParam
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.amadeus.Database
import com.pablichj.incubator.amadeus.common.DefaultTimeProvider
import com.pablichj.incubator.amadeus.common.ITimeProvider
import com.pablichj.incubator.amadeus.endpoint.accesstoken.*
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinationsRequest
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinationsResponse
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinatiosUseCase
import com.pablichj.incubator.amadeus.endpoint.hotellist.ListHotelByCityResponse
import com.pablichj.incubator.amadeus.endpoint.hotellist.ListHotelsByCityRequest
import com.pablichj.incubator.amadeus.endpoint.hotellist.ListHotelsByCityUseCase
import com.pablichj.incubator.amadeus.endpoint.hotelsearch.ManyHotelOffersRequest
import com.pablichj.incubator.amadeus.endpoint.hotelsearch.ManyHotelOffersResponse
import com.pablichj.incubator.amadeus.endpoint.hotelsearch.ManyHotelOffersUseCase
import com.pablichj.templato.component.core.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AmadeusDemoComponent(
    private val database: Database
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val timeProvider: ITimeProvider = DefaultTimeProvider()
    private val accessTokenDao = AccessTokenDaoDelight(
        database,
        timeProvider
    )

    private val console = mutableStateOf("")

    override fun start() {
        super.start()
        println("AmadeusDemoComponent::start()")
        output("AmadeusDemoComponent::start()")
    }

    override fun stop() {
        super.start()
        println("AmadeusDemoComponent::stop()")
        output("AmadeusDemoComponent::stop()")
    }

    private fun getAccessToken() {
        coroutineScope.launch {
            val tokenResponse = GetAccessTokenUseCase(Dispatchers).doWork(
                GetAccessTokenRequest(
                    ApiCredentials.apiKey,
                    ApiCredentials.apiSecret,
                    GetAccessTokenUseCase.AccessTokenGrantType
                )
            )
            when (tokenResponse) {
                is GetAccessTokenResponse.Error -> {
                    output("Error fetching access token: ${tokenResponse.error}")
                }
                is GetAccessTokenResponse.Success -> {
                    output("Get Token Success: ${tokenResponse.accessToken.accessToken}")
                    accessTokenDao.insert(tokenResponse.accessToken)
                    output("Insert Token Success: ${tokenResponse.accessToken.accessToken}")
                }
            }
        }
    }

    private fun getHotelsByCity() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCase(
                Dispatchers,
                accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val hotelListResult = ListHotelsByCityUseCase(
                Dispatchers
            ).doWork(
                //?cityCode=PAR&radius=1&radiusUnit=KM&hotelSource=ALL
                ListHotelsByCityRequest(
                    accessToken,
                    listOf(
                        QueryParam.CityCode("PAR"),
                        QueryParam.Radius("1"),
                        QueryParam.RadiusUnit("KM"),
                        QueryParam.HotelSource("ALL")
                    )
                )
            )

            when (hotelListResult) {
                is ListHotelByCityResponse.Error -> {
                    output("Error fetching hotel list: ${hotelListResult.error}")
                }
                is ListHotelByCityResponse.Success -> {
                    output("Success fetching hotel list: ${hotelListResult.hotelList}")
                }
            }

        }
    }

    private fun getManyHotelsOffers() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCase(
                Dispatchers,
                accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val manyHotelOffersResult = ManyHotelOffersUseCase(
                Dispatchers
            ).doWork(
                //?hotelIds=MCLONGHM&adults=1&checkInDate=2023-11-22&roomQuantity=1&paymentPolicy=NONE&bestRateOnly=true
                ManyHotelOffersRequest(
                    accessToken,
                    listOf(
                        QueryParam.HotelIds("MCLONGHM"),
                        QueryParam.Adults("1"),
                    )
                )
            )

            when (manyHotelOffersResult) {
                is ManyHotelOffersResponse.Error -> {
                    output("Error fetching hotel list: ${manyHotelOffersResult.error}")
                }
                is ManyHotelOffersResponse.Success -> {
                    output("Success fetching hotel list: ${manyHotelOffersResult.manyHotelOffers}")
                }
            }

        }
    }

    private fun getFlightDestinations() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCase(
                Dispatchers,
                accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val flightsDestinationsResult = GetFlightDestinatiosUseCase(
                Dispatchers
            ).doWork(
                // origin=PAR&maxPrice=200
                GetFlightDestinationsRequest(
                    accessToken,
                    listOf(
                        QueryParam.Origin("PAR"),
                        QueryParam.MaxPrice("600")
                    )
                )
            )

            when (flightsDestinationsResult) {
                is GetFlightDestinationsResponse.Error -> {
                    output("Error fetching flights: ${flightsDestinationsResult.error}")
                }
                is GetFlightDestinationsResponse.Success -> {
                    output("Success fetching flights: ${flightsDestinationsResult.hotelList}")
                }
            }

        }
    }

    private fun output(text: String) {
        console.value += "\n$text"
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content(modifier: Modifier) {
        Column(modifier.fillMaxSize()) {
            Spacer(Modifier.fillMaxWidth().height(24.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Welcome to Amadeus API",
                textAlign = TextAlign.Center
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
            ) {
                Button(
                    onClick = {
                        getAccessToken()
                    }
                ) {
                    Text("Get Access Token")
                }
                Button(
                    onClick = {
                        getHotelsByCity()
                    }
                ) {
                    Text("Get Hotels By City")
                }
                Button(
                    onClick = {
                        getManyHotelsOffers()
                    }
                ) {
                    Text("Get Many Hotel Offers")
                }
                Button(
                    onClick = {
                        // getFlightDestinations()
                    }
                ) {
                    Text("Get Flight Destinations")
                }
                Button(
                    onClick = {
                        console.value = ""
                    }
                ) {
                    Text("Clear")
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
                    .background(Color.White),
                text = console.value
            )
        }
    }

}