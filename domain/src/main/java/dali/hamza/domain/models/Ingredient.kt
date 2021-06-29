package dali.hamza.domain.models

import com.squareup.moshi.Json

data class Ingredient(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "image") val image: String,
)