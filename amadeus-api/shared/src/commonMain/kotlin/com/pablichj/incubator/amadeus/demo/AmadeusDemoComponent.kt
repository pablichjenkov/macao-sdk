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
import com.pablichj.incubator.amadeus.endpoint.city.CitySearchRequest
import com.pablichj.incubator.amadeus.endpoint.city.CitySearchResponse
import com.pablichj.incubator.amadeus.endpoint.city.CitySearchUseCase
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinationsRequest
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinationsResponse
import com.pablichj.incubator.amadeus.endpoint.fligths.destination.GetFlightDestinatiosUseCase
import com.pablichj.incubator.amadeus.endpoint.hotels.HotelByCityResponse
import com.pablichj.incubator.amadeus.endpoint.hotels.HotelsByCityRequest
import com.pablichj.incubator.amadeus.endpoint.hotels.HotelsByCityUseCase
import com.pablichj.incubator.amadeus.endpoint.offers.*
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

    private fun getCitiesByKeyword() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers,
                accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val citySearchResult = CitySearchUseCase(
                Dispatchers
            ).doWork(
                //?countryCode=FR&keyword=PARIS&max=10
                CitySearchRequest(
                    accessToken,
                    listOf(
                        QueryParam.CountryCode("US"),// todo remove hardcoded values
                        QueryParam.Max("5"),
                        QueryParam.Keyword("Miami")
                    )
                )
            )

            when (citySearchResult) {
                is CitySearchResponse.Error -> {
                    output("Error fetching hotel list: ${citySearchResult.error}")
                }
                is CitySearchResponse.Success -> {
                    output(citySearchResult.citySearchBody)
                }
            }

        }
    }

    private fun getHotelsByCity() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers,
                accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val hotelListResult = HotelsByCityUseCase(
                Dispatchers
            ).doWork(
                //?cityCode=PAR&radius=1&radiusUnit=KM&hotelSource=ALL
                HotelsByCityRequest(
                    accessToken,
                    listOf(
                        QueryParam.CityCode("PAR"),// todo remove hardcoded values
                        QueryParam.Radius("1"),
                        QueryParam.RadiusUnit("KM"),
                        QueryParam.HotelSource("ALL")
                    )
                )
            )

            when (hotelListResult) {
                is HotelByCityResponse.Error -> {
                    output("Error fetching hotel list: ${hotelListResult.error}")
                }
                is HotelByCityResponse.Success -> {
                    hotelListResult.hotelListingBody.data.forEach {
                        output(
                            """Hotel ID: ${it.hotelId}
                               |Geocode: ${it.geoCode}
                               |Dupe ID: ${it.dupeId}
                               -----
                            """.trimMargin()
                        )
                    }
                }
            }

        }
    }

    private fun getMultiHotelsOffers() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers,
                accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val multiHotelOffersResult = MultiHotelOffersUseCase(
                Dispatchers
            ).doWork(
                //?hotelIds=MCLONGHM&adults=1&checkInDate=2023-11-22&roomQuantity=1&paymentPolicy=NONE&bestRateOnly=true
                MultiHotelOffersRequest(
                    accessToken,
                    listOf(
                        QueryParam.HotelIds("MCLONGHM"),// ACPAR243 todo remove hardcoded values
                        QueryParam.Adults("1"),
                    )
                )
            )

            when (multiHotelOffersResult) {
                is MultiHotelOffersResponse.Error -> {
                    output("Error fetching hotel list: ${multiHotelOffersResult.error}")
                }
                is MultiHotelOffersResponse.Success -> {
                    multiHotelOffersResult.manyHotelOffers.data.forEach {
                        output(
                            """Hotel ID: ${it.hotel.hotelId}
                               |Available: ${it.available}
                               |Type: ${it.type}
                            """.trimMargin()
                        )
                        output("Offers: ")
                        it.offers.forEach {
                            output(
                                """Offer ID: ${it.id}
                                   |Base Price: ${it.price.base}
                                   |Checkin: ${it.checkInDate}
                            """.trimMargin()
                            )
                        }
                    }
                }
            }

        }
    }

    private fun getOffer() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
                Dispatchers,
                accessTokenDao
            ).doWork()

            if (accessToken == null) {
                output("No saved token")
                return@launch
            } else {
                output("Using saved token: ${accessToken.accessToken}")
            }

            val getOfferResult = GetOfferUseCase(
                Dispatchers
            ).doWork(
                GetOfferRequest(
                    accessToken,
                    "TSXOJ6LFQ2"
                )
            )

            when (getOfferResult) {
                is GetOfferResponse.Error -> {
                    output("Error fetching hotel list: ${getOfferResult.error}")
                }
                is GetOfferResponse.Success -> {
                    output(getOfferResult.offer)
                }
            }

        }
    }

    private fun getFlightDestinations() {
        coroutineScope.launch {
            val accessToken = ResolveAccessTokenUseCaseSource(
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
                        getCitiesByKeyword()
                    }
                ) {
                    Text("City Search")
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
                        getMultiHotelsOffers()
                    }
                ) {
                    Text("Get Multi Hotel Offers")
                }
                Button(
                    onClick = {
                        getMultiHotelsOffers()
                    }
                ) {
                    Text("Get Multi Hotel Offers")
                }
                Button(
                    onClick = {
                        getOffer()
                    }
                ) {
                    Text("Get Offer")
                }
                /*Button(
                    onClick = {
                        getFlightDestinations()
                    }
                ) {
                    Text("Get Flight Destinations")
                }*/
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