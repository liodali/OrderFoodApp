package dali.hamza.core.datasource.network.models

import com.squareup.moshi.Json
import dali.hamza.domain.Order

data class Status(
    @Json(name = "success") val success: Boolean,
    @Json(name = "statusCode") val statusCode: Int,
    @Json(name = "message") val message: String,
)




data class OrderResponse(
    @Json(name = "status")  val status: Status,
    @Json(name = "data") val data: List<Order>
)