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
import androidx.compose.ui.unit.sp
import com.pablichj.incubator.amadeus.Database
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.common.DefaultTimeProvider
import com.pablichj.incubator.amadeus.common.ITimeProvider
import com.pablichj.incubator.amadeus.endpoint.accesstoken.*
import com.pablichj.incubator.amadeus.endpoint.airport.AirportAndCitySearchRequest
import com.pablichj.incubator.amadeus.endpoint.airport.AirportAndCitySearchResponse
import com.pablichj.incubator.amadeus.endpoint.airport.AirportAndCitySearchUseCase
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinationsRequest
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinationsResponse
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinationsUseCase
import com.pablichj.incubator.amadeus.endpoint.offers.*
import com.pablichj.incubator.amadeus.endpoint.offers.flight.*
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.FlightOffer
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.FlightOffersConfirmationRequestBody
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.FlightOffersConfirmationRequestBodyBoxing
import com.pablichj.templato.component.core.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AirportDemoComponent(
    private val database: Database
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val timeProvider: ITimeProvider = DefaultTimeProvider()
    private val accessTokenDao = AccessTokenDaoDelight(
        database,
        timeProvider
    )

    private var flightOffers: List<FlightOffer>? = null
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
                    accessTokenDao.insert(tokenResponse.accessToken)
                    output("SQDelight Insert Token Success: ${tokenResponse.accessToken.accessToken}")
                }
            }
        }
    }

    private fun searchAirportByKeyword() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers, accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val airportByKeywordResult = AirportAndCitySearchUseCase(
                Dispatchers
            ).doWork(
                // ?subType=CITY&keyword=MUC&page%5Blimit%5D=10&page%5Boffset%5D=0&sort=analytics.travelers.score&view=FULL
                AirportAndCitySearchRequest(
                    accessToken, listOf(
                        QueryParam.Keyword("New Orleans"),
                        QueryParam.SubType("AIRPORT")
                    )
                )
            )

            when (airportByKeywordResult) {
                is AirportAndCitySearchResponse.Error -> {
                    output("Error fetching Airports: ${airportByKeywordResult.error}")
                }
                is AirportAndCitySearchResponse.Success -> {
                    airportByKeywordResult.responseBody.data.forEach {
                        output(
                            """
                            Airport Name: ${it.name}
                            Airport Detailed Name: ${it.detailedName}
                            Airport Id: ${it.id}
                            Location Subtype: ${it.subType}
                            Airport Country: ${it.address.countryName}
                            Airport State: ${it.address.stateCode}
                            Airport City: ${it.address.cityName}
                        """.trimIndent()
                        )
                    }
                }
            }

        }
    }

    private fun searchFlightOffersGet() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers, accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val flightOffersResult = FlightOffersUseCase(
                Dispatchers
            ).doWork(
                FlightOffersRequest(
                    accessToken,
                    TestData.flightOffersRequestBody
                )
            )

            when (flightOffersResult) {
                is CallResult.Error -> {
                    output("Error fetching flight offers: ${flightOffersResult.error}")
                }
                is CallResult.Success<FlightOffersResponse> -> {
                    flightOffers = flightOffersResult.responseBody.data
                    flightOffersResult.responseBody.data.forEach {
                        output(
                            """
                            Offer Id: ${it.id}
                            Offer type: ${it.type}
                            Offer itineraries: ${it.itineraries}
                            Offer lastTicketingDate: ${it.lastTicketingDate}
                            Offer instantTicketingRequired: ${it.instantTicketingRequired}
                            Offer is oneWay: ${it.oneWay}
                            Offer source: ${it.source}
                            Offer nonHomogeneous: ${it.nonHomogeneous}
                            Offer nonHomogeneous: ${it.nonHomogeneous}
                        """.trimIndent()
                        )
                        output("Itineraries:")
                        it.itineraries.forEach {
                            output(
                                """ 
                                Itinerary duration: ${it.duration}
                                Itinerary segments: ${it.segments}
                            """.trimIndent()
                            )
                        }
                        output("Pricing:")
                        output("Price currency: ${it.price.currency}")
                        output("Price base: ${it.price.base}")
                        output("Price total: ${it.price.total}")
                        output("Price grandTotal: ${it.price.grandTotal}")
                        output("Price Fees:")
                        it.price.fees.forEach {
                            output(
                                """ 
                                Fee amount: ${it.amount}
                                Fee type: ${it.type}
                            """.trimIndent()
                            )
                        }
                    }
                    output(flightOffersResult.responseBody.toJson())
                }
            }

        }
    }

    private fun confirmFlightOffersGet() {
        coroutineScope.launch {

            val offerToVerify = flightOffers?.firstOrNull() ?: run {
                output("No Offer selected to confirm")
                return@launch
            }

            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers, accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val flightOffersConfirmationResult = FlightOffersConfirmationUseCase(
                Dispatchers
            ).doWork(
                FlightOffersConfirmationRequest(
                    accessToken,
                    FlightOffersConfirmationRequestBodyBoxing(
                        data = FlightOffersConfirmationRequestBody(
                            type = "flight-offers-pricing",
                            flightOffers = listOf(offerToVerify)
                        )
                    )
                )
            )

            when (flightOffersConfirmationResult) {
                is CallResult.Error -> {
                    output("Error fetching flight offers: ${flightOffersConfirmationResult.error}")
                }
                is CallResult.Success<FlightOffersConfirmationResponse> -> {
                    flightOffersConfirmationResult.responseBody.data.forEach {
                        output(
                            """
                            Offer Id: ${it.id}
                            Offer type: ${it.type}
                            Offer itineraries: ${it.itineraries}
                            Offer lastTicketingDate: ${it.lastTicketingDate}
                            Offer instantTicketingRequired: ${it.instantTicketingRequired}
                            Offer is oneWay: ${it.oneWay}
                            Offer source: ${it.source}
                            Offer nonHomogeneous: ${it.nonHomogeneous}
                            Offer nonHomogeneous: ${it.nonHomogeneous}
                        """.trimIndent()
                        )
                        output("Itineraries:")
                        it.itineraries.forEach {
                            output(
                                """ 
                                Itinerary duration: ${it.duration}
                                Itinerary segments: ${it.segments}
                            """.trimIndent()
                            )
                        }
                        output("Pricing:")
                        output("Price currency: ${it.price.currency}")
                        output("Price base: ${it.price.base}")
                        output("Price total: ${it.price.total}")
                        output("Price grandTotal: ${it.price.grandTotal}")
                        output("Price Fees:")
                        it.price.fees.forEach {
                            output(
                                """ 
                                Fee amount: ${it.amount}
                                Fee type: ${it.type}
                            """.trimIndent()
                            )
                        }
                    }
                    output(flightOffersConfirmationResult.responseBody.toJson())
                }
            }

        }
    }

    private fun getFlightDestinations() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers, accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val flightsDestinationsResult = GetFlightDestinationsUseCase(
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
                    output("Success fetching flights: ${flightsDestinationsResult.flightDestinationsBody}")
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
                text = "Welcome to Amadeus Flight Booking API",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
            ) {
                Button(onClick = {
                    getAccessToken()
                }) {
                    Text("Get Access Token")
                }
                Button(onClick = {
                    searchAirportByKeyword()
                }) {
                    Text("Search Airport")
                }
                Button(onClick = {
                    searchFlightOffersGet()
                }) {
                    Text("Search Flight Offers")
                }
                Button(onClick = {
                    confirmFlightOffersGet()
                }) {
                    Text("Confirm Flight Offers")
                }
                /*Button(
                    onClick = {
                        getFlightDestinations()
                    }
                ) {
                    Text("Get Flight Destinations")
                }*/
                Button(onClick = {
                    console.value = ""
                }) {
                    Text("Clear")
                }
            }
            Text(
                modifier = Modifier.fillMaxSize().padding(8.dp)
                    .verticalScroll(rememberScrollState()).background(Color.White),
                text = console.value
            )
        }
    }

}
