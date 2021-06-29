package dali.hamza.core.interactor

import dali.hamza.domain.interactor.FlowIResponseUseCase
import dali.hamza.domain.interactor.FlowIResponseUseCase0
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.repository.ICategoryRepository
import dali.hamza.domain.repository.IIngredientsRepository
import dali.hamza.domain.repository.IOrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveAllCategoriesUseCase @Inject constructor(
    private val repository: ICategoryRepository
) : FlowIResponseUseCase0 {
    override suspend fun invoke(): Flow<IResponse> {
        return repository.getAll()
    }
}