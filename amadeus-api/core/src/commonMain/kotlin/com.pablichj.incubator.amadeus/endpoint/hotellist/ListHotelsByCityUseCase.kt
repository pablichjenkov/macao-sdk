package com.pablichj.incubator.amadeus.endpoint.hotellist

import AmadeusError
import apiHost
import com.pablichj.incubator.amadeus.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListHotelsByCityUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<ListHotelsByCityRequest, ListHotelByCityResponse> {
    //cityCode=PAR&radius=5&radiusUnit=KM&hotelSource=ALL
    override suspend fun doWork(params: ListHotelsByCityRequest): ListHotelByCityResponse {
        val resp = withContext(dispatcher.Unconfined) {
            httpClient.get(hotelOffersUrl) {
                url {
                    parameters.append("cityCode", "PAR")
                    //parameters.append("adults", "1")
                    parameters.append("radius", "1")
                    parameters.append("radiusUnit", "KM")
                    parameters.append("hotelSource", "ALL")
                    //parameters.append("page[offset]", "1")
                    //parameters.append("page[limit]", "2")
                    //parameters.append("checkInDate", "2023-07-10")
                    //parameters.append("checkOutDate", "2023-07-17")
                    //parameters.append("roomQuantity", "1")
                }
                header(HttpHeaders.Authorization, params.accessToken.authorization)
            }
        }

        return if (resp.status.isSuccess()) {
            ListHotelByCityResponse.Success(resp.bodyAsText())
        } else {
            ListHotelByCityResponse.Error(resp.body<AmadeusError>())
        }

    }

    companion object {
        private val hotelOffersUrl = "$apiHost/v1/reference-data/locations/hotels/by-city"
    }

}