package com.indra.coinbaseapp.comm.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SubscribeAction(
    @Json(name = "type") val type: String = "subscribe",
    @Json(name = "product_ids") val productIds: List<String>,
    @Json(name = "channels") val channels: List<TickerRequest>
)
