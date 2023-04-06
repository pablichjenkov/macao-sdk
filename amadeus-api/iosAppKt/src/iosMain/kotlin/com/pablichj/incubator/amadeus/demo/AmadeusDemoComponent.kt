package com.pablichj.incubator.amadeus.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.amadeus.Amadeus
import com.pablichj.incubator.amadeus.Database
import com.pablichj.incubator.amadeus.endpoint.accesstoken.AccessTokenManager
import com.pablichj.incubator.amadeus.endpoint.accesstoken.IAccessTokenManager
import com.pablichj.incubator.amadeus.model.AccessToken
import com.pablichj.incubator.uistate3.node.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AmadeusDemoComponent(
    private val database: Database
) : Component() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val accessTokenManager: IAccessTokenManager = AccessTokenManager.create(database)
    private var token: AccessToken? = null

    private val amadeusApi = Amadeus.Builder(
        ApiCredentials.apiKey,
        ApiCredentials.apiSecret
    ).build()

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
            token = amadeusApi.getRemoteAccessToken()?.also {
                accessTokenManager.insert(it)
            }
            println("AmadeusDemoComponent::token = $token")
        }
    }

    private fun getHotelOffers() {
        coroutineScope.launch {
            var tokenCopy = token
            /*if (tokenCopy != null) {
                val hotelOffers = amadeusApi.getMultiHotelOffers(tokenCopy)
                println("AmadeusDemoComponent::hotelOffers::ram-token = ${hotelOffers}")
            }*/
            /*tokenCopy = accessTokenManager.lastOrNull()
            if (tokenCopy != null) {
                //val hotelOffers = amadeusApi.getMultiHotelOffers(tokenCopy)
                println("AmadeusDemoComponent::hotelOffers::stored-token = ${tokenCopy}")
            }*/
            accessTokenManager.all().forEach {
                println("AmadeusDemoComponent::hotelOffers::all-stored-token = ${it}")
            }
        }
    }

    @Composable
    internal fun Content(modifier: Modifier) {
        Column (modifier.fillMaxSize()) {
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
        }
    }

}