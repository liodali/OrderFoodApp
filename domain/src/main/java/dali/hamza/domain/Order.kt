package dali.hamza.domain

import com.squareup.moshi.Json

data class Order(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "addon") val addon: List<Addon>,
    @Json(name = "addon") val quantity: Int,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "alertedAt") val alertedAt: String,
    @Json(name = "expiredAt") val expiredAt: String,
)

data class Addon(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "quantity") val quantity: Int,
)