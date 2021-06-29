package dali.hamza.core.repository

import dali.hamza.core.common.data
import dali.hamza.core.datasource.network.AppClientApi
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.Ingredient
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.domain.repository.ICategoryRepository
import dali.hamza.domain.repository.IIngredientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val clientApi: AppClientApi
) : ICategoryRepository {


    override suspend fun getAll(): Flow<IResponse> {
        return flow {
            val response = clientApi.getCategories().data {
                it.data
            }
            emit(response)
        }
    }
}