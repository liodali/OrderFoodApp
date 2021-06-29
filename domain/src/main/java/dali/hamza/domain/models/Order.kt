package dali.hamza.domain

import com.squareup.moshi.Json

data class Order(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "addon") val addon: List<Addon>,
    @field:Json(name = "quantity") val quantity: Int,
    @field:Json(name = "createdAt") val createdAt: String,
    @field:Json(name = "alertedAt") val alertedAt: String,
    @field:Json(name = "expiredAt") val expiredAt: String,
)

data class Addon(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "quantity") val quantity: Int,
)