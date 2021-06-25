package dali.hamza.core.interactor

import dali.hamza.domain.interactor.FlowIResponseUseCase0
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.repository.IOrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveAllOrderUseCase @Inject constructor(
    private val repository: IOrderRepository
) : FlowIResponseUseCase0 {
    override suspend fun invoke(): Flow<IResponse> {
        return repository.getAll()
    }
}