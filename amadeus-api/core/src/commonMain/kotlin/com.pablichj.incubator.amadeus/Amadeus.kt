package com.pablichj.incubator.amadeus

import com.pablichj.incubator.amadeus.model.AccessToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Amadeus private constructor(
    private val baseUrl: String,
    private val clientId: String,
    private val clientSecret: String,
    //private val logLevel: HttpLoggingInterceptor.Level,
    private val customAppId: String?,
    private val customAppVersion: String?,
    private val dispatcher: CoroutineDispatcher
) {
    private val apiHost = "https://test.api.amadeus.com"
    private val tokenUrl = "https://test.api.amadeus.com/v1/security/oauth2/token"
    private val accessTokenGrantType = "client_credentials"

    suspend fun getRemoteAccessToken(): AccessToken? {

        val resp = withContext(dispatcher) {
            /*client.post(tokenUrl) {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(
                    "grant_type=${accessTokenGrantType}&client_id=${clientId}&client_secret=${clientSecret}"
                )
            }*/
            httpClient.submitForm(
                url = tokenUrl,
                formParameters = Parameters.build {
                    append("grant_type", accessTokenGrantType)
                    append("client_id", clientId)
                    append("client_secret", clientSecret)
                }
            )
        }

        return if (resp.status.isSuccess()) {
            resp.body<AccessToken>()
        } else {
            null
        }

    }

    private val hotelOffersUrl = "$apiHost/v1/reference-data/locations/hotels/by-city"
    //cityCode=PAR&radius=5&radiusUnit=KM&hotelSource=ALL
    suspend fun getMultiHotelOffers(accessToken: AccessToken): String {
        val resp = withContext(dispatcher) {
            httpClient.get(hotelOffersUrl) {
                url {
                    parameters.append("cityCode", "PAR")
                    //parameters.append("adults", "1")
                    parameters.append("radius", "20")
                    parameters.append("radiusUnit", "KM")
                    parameters.append("hotelSource", "ALL")
                    //parameters.append("checkInDate", "2023-07-10")
                    //parameters.append("checkOutDate", "2023-07-17")
                    //parameters.append("roomQuantity", "1")
                }
                header(HttpHeaders.Authorization, accessToken.authorization)
            }
        }

        return resp.bodyAsText()
    }

    private val flightOfferUrl = "https://test.api.amadeus.com/v1/shopping/flight-destinations"

    suspend fun getFlightDestination(
        accessToken: AccessToken,
        origin: String,
        maxPrice: String
    ): String {

        val resp = withContext(dispatcher) {
            httpClient.get(flightOfferUrl) {
                url {
                    parameters.append("origin", origin)
                    parameters.append("maxPrice", maxPrice)
                }
                header(HttpHeaders.Authorization, accessToken.authorization)
            }
        }

        return resp.bodyAsText()
    }

    class Builder internal constructor() {
        private var hostName: String = Hosts.TEST.value
        private lateinit var clientId: String
        private lateinit var clientSecret: String
        //private var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.NONE
        private var customAppId: String? = null
        private var customAppVersion: String? = null
        private var dispatcher: CoroutineDispatcher = Dispatchers.Default//Dispatchers.IO

        constructor(clientId: String, clientSecret: String) : this() {
            this.clientId = clientId
            this.clientSecret = clientSecret
        }

        enum class Hosts(val value: String) {
            TEST("https://test.api.amadeus.com/"),
            PRODUCTION("https://api.amadeus.com/")
        }

        enum class LogLevel(/*val value: HttpLoggingInterceptor.Level*/) {
            /** No logs. */
            NONE(/*HttpLoggingInterceptor.Level.NONE*/),

            /**
             * Logs request and response lines.
             *
             * Example:
             * ```
             * --> POST /greeting http/1.1 (3-byte body)
             *
             * <-- 200 OK (22ms, 6-byte body)
             * ```
             */
            BASIC(/*HttpLoggingInterceptor.Level.BASIC*/),

            /**
             * Logs request and response lines and their respective headers.
             *
             * Example:
             * ```
             * --> POST /greeting http/1.1
             * Host: example.com
             * Content-Type: plain/text
             * Content-Length: 3
             * --> END POST
             *
             * <-- 200 OK (22ms)
             * Content-Type: plain/text
             * Content-Length: 6
             * <-- END HTTP
             * ```
             */
            HEADERS(/*HttpLoggingInterceptor.Level.HEADERS*/),

            /**
             * Logs request and response lines and their respective headers and bodies (if present).
             *
             * Example:
             * ```
             * --> POST /greeting http/1.1
             * Host: example.com
             * Content-Type: plain/text
             * Content-Length: 3
             *
             * Hi?
             * --> END POST
             *
             * <-- 200 OK (22ms)
             * Content-Type: plain/text
             * Content-Length: 6
             *
             * Hello!
             * <-- END HTTP
             * ```
             */
            BODY(/*HttpLoggingInterceptor.Level.BODY*/)
        }

        /**
         * Set client default host name.
         * Default Hosts.TEST.
         */
        fun setHostName(hostName: Hosts) = apply { this.hostName = hostName.value }

        /**
         * Required: set client id.
         */
        fun setClientId(clientId: String) = apply { this.clientId = clientId }

        /**
         * Required: set client secret.
         */
        fun setClientSecret(clientSecret: String) = apply { this.clientSecret = clientSecret }

        /**
         * Set client log level.
         * Default LogLevel.NONE.
         */
        fun setLogLevel(logLevel: LogLevel) = apply { /*this.logLevel = logLevel.value*/ }

        fun setCustomAppIdAndVersion(appId: String, appVersion: String) = apply {
            this.customAppId = appId
            this.customAppVersion = appVersion
        }

        /**
         * Set client coroutine dispatcher, this should be use for tests purposes only.
         * Default Dispatchers.IO.
         */
        fun setDispatcher(dispatcher: CoroutineDispatcher) = apply { this.dispatcher = dispatcher }

        fun build() = Amadeus(
            hostName,
            clientId,
            clientSecret,
            //logLevel,
            customAppId,
            customAppVersion,
            dispatcher
        )
    }

}