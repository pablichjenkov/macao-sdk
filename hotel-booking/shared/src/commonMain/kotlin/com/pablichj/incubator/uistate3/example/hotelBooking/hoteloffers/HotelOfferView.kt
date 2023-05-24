package com.pablichj.incubator.uistate3.example.hotelBooking.hoteloffers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HotelOfferView(
    modifier: Modifier = Modifier,
    offer: HotelOfferSearch.Offer,
    onOfferClick: () -> Unit
) {
    // val context = LocalContext.current
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp,
        onClick = { onOfferClick() }
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            /*AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("https://images.unsplash.com/photo-1550547660-d9450f859349?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=930&q=80")
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = "Content Description"
            )*/
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    buildRoomInfo(offer.room),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
                Text(
                    buildCheckInOutDateText(offer),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    offer.category ?: "No category",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    buildPriceText(offer),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Blue
                    )
                )
            }
        }
    }
}

fun buildRoomInfo(room: HotelOfferSearch.RoomDetails?): String {
    return StringBuilder().apply {
        appendLine("${room?.typeEstimated?.category} - ${room?.typeEstimated?.bedType}")
        appendLine(room?.description?.text ?: "room info not available")
    }.toString()
}

private fun buildCheckInOutDateText(offer: HotelOfferSearch.Offer): String {
    return "${offer.checkInDate} to ${offer.checkOutDate}"
}

private fun buildPriceText(offer: HotelOfferSearch.Offer): String {
    val symbol = offer.price.currency
    offer.price.variations
    return """
        Base price: ${offer.price.base} ($symbol)
        ${buildTaxesTotal(offer.price.taxes)} ($symbol)
        Total:      ${offer.price.total} ($symbol)
    """.trimIndent()
}

private fun buildTaxesTotal(taxes: List<HotelOfferSearch.HotelTax>?): String {
    taxes ?: return "No Taxes"
    return StringBuilder().apply {
        taxes.forEach {
            appendLine("Tax:       ${it.amount}")
        }
    }.toString()
}