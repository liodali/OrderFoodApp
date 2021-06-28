package dali.hamza.orderfoodapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dali.hamza.core.interactor.RetrieveAllOrderUseCase
import dali.hamza.domain.Order
import dali.hamza.domain.models.IResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val retrieveAllOrderUseCase: RetrieveAllOrderUseCase
) : ViewModel() {

    var isLoading: Boolean by mutableStateOf(true)


    private var mutableFlowOrders: MutableStateFlow<IResponse?> = MutableStateFlow(null)
    private var stateFlowStateOrders: StateFlow<IResponse?> = mutableFlowOrders

    private var stateFlowOrders: MutableStateFlow<List<Order>> = MutableStateFlow(emptyList())


    fun getAllOrders(): StateFlow<IResponse?> = stateFlowStateOrders

    fun orders(): StateFlow<List<Order>> = stateFlowOrders

    fun init(list: List<Order>) {
        stateFlowOrders.value = list
    }

    fun removeOrder(order: Order) {
        val list = stateFlowOrders.value.toMutableList()
        list.remove(order)
        stateFlowOrders.value = list

    }


    fun retrieveAllOrder() {
        viewModelScope.launch {
            retrieveAllOrderUseCase.invoke().collect { response ->
                mutableFlowOrders.value = response
                isLoading = false
            }
        }
    }

}