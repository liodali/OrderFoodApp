package dali.hamza.domain.repository

import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.Ingredient
import kotlinx.coroutines.flow.Flow

interface IIngredientsRepository : IRepository<Ingredient> {

    suspend fun getIngredientsByCategory(idCategory: Int): Flow<IResponse>
    suspend fun filterIngredientsByCategory(idCategory: Int, query: String): Flow<IResponse>

}