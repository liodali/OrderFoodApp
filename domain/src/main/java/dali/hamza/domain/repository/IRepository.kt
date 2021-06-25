package dali.hamza.domain.repository

import dali.hamza.domain.models.IResponse
import kotlinx.coroutines.flow.Flow

interface IRepository<T> {

    fun getAll(): Flow<IResponse>

}