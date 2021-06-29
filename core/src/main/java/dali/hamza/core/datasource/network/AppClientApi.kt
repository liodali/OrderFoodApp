package dali.hamza.core.datasource.network

import dali.hamza.core.datasource.network.models.AppResponse
import dali.hamza.core.datasource.network.models.OrderResponse
import dali.hamza.domain.models.Category
import dali.hamza.domain.models.Ingredient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppClientApi {


    @GET("/orders/all")
    suspend fun getOrders(): Response<OrderResponse>


    @GET("/ingredient")
    suspend fun getIngredients(
        @Query("category") category: Int
    ): Response<AppResponse<Ingredient>>

    @GET("/category/all")
    suspend fun getCategories(
    ): Response<AppResponse<Category>>
}