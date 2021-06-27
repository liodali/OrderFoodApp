package dali.hamza.core.repository

import dali.hamza.core.common.data
import dali.hamza.core.datasource.network.AppClientApi
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.repository.IOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val clientApi: AppClientApi
) : IOrderRepository {


    override suspend fun getAll(): Flow<IResponse> {
        return flow {
          val response =   clientApi.getOrders().data {
                it.data
            }
            emit(response)
        }
    }
}