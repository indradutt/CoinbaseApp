package com.indra.coinbaseapp.comm.response

import com.squareup.moshi.Json

data class TickerResponse(
    @Json(name = "price") val price: Double
)
