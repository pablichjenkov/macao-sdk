package com.pablichj.incubator.amadeus

import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.Carriers
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.Dictionaries
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val RequestTimeoutMillis = 15_000L

internal val httpClient = HttpClient {
    install(HttpTimeout) {
        requestTimeoutMillis = RequestTimeoutMillis
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        /*filter { request ->
            request.url.host.contains("ktor.io")
        }*/
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
}

