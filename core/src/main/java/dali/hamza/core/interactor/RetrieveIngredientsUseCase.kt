package dali.hamza.core.interactor

import dali.hamza.domain.interactor.FlowIResponseUseCase
import dali.hamza.domain.interactor.FlowIResponseUseCase0
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.repository.IIngredientsRepository
import dali.hamza.domain.repository.IOrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveIngredientsUseCase @Inject constructor(
    private val repository: IIngredientsRepository
) : FlowIResponseUseCase<Int> {
    override suspend fun invoke(parameter: Int?): Flow<IResponse> {
        return repository.getIngredientsByCategory(parameter!!)
    }
}