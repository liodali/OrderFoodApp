package dali.hamza.core.datasource.network

import dali.hamza.core.datasource.network.models.OrderResponse
import retrofit2.Response
import retrofit2.http.GET

interface AppClientApi {


    @GET("/orders/all")
    suspend fun getOrders(): Response<OrderResponse>


}