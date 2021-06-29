package dali.hamza.core.repository

import dali.hamza.core.common.data
import dali.hamza.core.datasource.network.AppClientApi
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.Ingredient
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.domain.repository.IIngredientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IngredientsRepository @Inject constructor(
    private val clientApi: AppClientApi
) : IIngredientsRepository {
    override suspend fun getIngredientsByCategory(idCategory: Int): Flow<IResponse> {
        return flow {
            val response = clientApi.getIngredients(idCategory).data {
                it.data
            }
            emit(response)
        }
    }

    override suspend fun filterIngredientsByCategory(
        idCategory: Int,
        query: String
    ): Flow<IResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Flow<IResponse> {
        TODO("Not yet implemented")
    }
}