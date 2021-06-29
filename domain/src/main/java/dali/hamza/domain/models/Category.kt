package dali.hamza.domain.models

import com.squareup.moshi.Json

data class Category(
    @Json(name = "id") val id: Int,
    @Json(name = "category_name") val category_name: String,
)