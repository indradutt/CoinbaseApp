package com.indra.coinbaseapp.comm.request

import com.squareup.moshi.Json

data class TickerRequest(
    @Json(name = "name") val name: String = "ticker",
    @Json(name = "product_ids") val productIds: List<String>
)
