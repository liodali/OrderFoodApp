package dali.hamza.domain.repository

import dali.hamza.domain.models.IResponse
import kotlinx.coroutines.flow.Flow

interface IRepository<T> {

    suspend fun getAll(): Flow<IResponse>

}