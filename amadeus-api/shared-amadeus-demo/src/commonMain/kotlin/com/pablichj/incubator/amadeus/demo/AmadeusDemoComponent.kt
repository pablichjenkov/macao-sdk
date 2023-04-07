package com.pablichj.incubator.amadeus.demo

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
import com.pablichj.incubator.amadeus.endpoint.hotellist.ListHotelByCityResponse
import com.pablichj.incubator.amadeus.endpoint.hotellist.ListHotelsByCityRequest
import com.pablichj.incubator.amadeus.endpoint.hotellist.ListHotelsByCityUseCase
import com.pablichj.incubator.uistate3.node.Component
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
    }

    override fun stop() {
        super.start()
        println("AmadeusDemoComponent::stop()")
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

    private fun getHotelOffers() {
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
                ListHotelsByCityRequest(accessToken)
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

    private fun output(text: String) {
        console.value += "\n$text"
    }

    @Composable
    override fun Content(modifier: Modifier) {
        Column(modifier.fillMaxSize()) {
            Spacer(Modifier.fillMaxWidth().height(24.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Welcome to Amadeus API",
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.fillMaxWidth().height(24.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    getAccessToken()
                }
            ) {
                Text("Get Access Token")
            }
            Spacer(Modifier.fillMaxWidth().height(24.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    getHotelOffers()
                }
            ) {
                Text("Get Hotel Offers")
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