package com.pablichj.incubator.uistate3.example.hotelBooking.hotelsearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing

@Composable
internal fun HotelListingView(
    hotelListing: HotelListing,
    onHotelSelected: () -> Unit
) {
    // val context = LocalContext.current
    Box(Modifier.fillMaxWidth().wrapContentHeight()
        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
        //.background(Color.LightGray)
        .shadow(4.dp)
        .clickable { onHotelSelected() }
    ) {
        /*Card(
            backgroundColor = Color.LightGray
        ) {*/
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
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    hotelListing.name ?: "No name provided",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    hotelListing.chainCode ?: "Is not part of any hotel chain.",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Blue
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
    //}
}