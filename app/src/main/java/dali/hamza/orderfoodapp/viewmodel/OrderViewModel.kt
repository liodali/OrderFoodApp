package dali.hamza.orderfoodapp.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dali.hamza.core.common.DateExpiration
import dali.hamza.core.interactor.RetrieveAllOrderUseCase
import dali.hamza.domain.Order
import dali.hamza.domain.models.IResponse
import dali.hamza.orderfoodapp.model.SaverOrderUI
import kotlinx.coroutines.Dispatchers.IO
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

    var allOrderAreCleared: Boolean by mutableStateOf(false)

    private var mutableOrderExpInStateFlow: MutableStateFlow<MutableList<Pair<Int, SaverOrderUI>>> =
        MutableStateFlow(emptyList<Pair<Int, SaverOrderUI>>().toMutableList())

    fun getSaverOrder(): StateFlow<List<Pair<Int, SaverOrderUI>>> = mutableOrderExpInStateFlow

    fun addSaverOrderExpIn(orderExpIn: SaverOrderUI) {
        val list: MutableList<Pair<Int, SaverOrderUI>> = mutableOrderExpInStateFlow.value
        val saverOrder = list.firstOrNull {
            it.first == orderExpIn.order.id
        }
        when (saverOrder == null) {
            true -> {
                list.add(Pair(orderExpIn.order.id, orderExpIn))
                mutableOrderExpInStateFlow.value = list
            }
            else -> {
                val index = list.indexOf(saverOrder)
                list[index] = Pair(orderExpIn.order.id, orderExpIn)
                mutableOrderExpInStateFlow.value = list
            }
        }

    }

    fun clearSaverOrderExpIn() {
        mutableOrderExpInStateFlow.value.clear()
    }

    private var mutableFlowOrders: MutableStateFlow<IResponse?> = MutableStateFlow(null)
    private var stateFlowStateOrders: StateFlow<IResponse?> = mutableFlowOrders

    private var stateFlowOrders: MutableStateFlow<MutableList<Order>> =
        MutableStateFlow(emptyList<Order>().toMutableList())


    fun getResponseOrders(): StateFlow<IResponse?> = stateFlowStateOrders

    fun orders(): StateFlow<List<Order>> = stateFlowOrders

    fun init(list: List<Order>) {
        if (!allOrderAreCleared)
            stateFlowOrders.value = list.toMutableList()
    }

    fun removeOrder(order: Order) {
        val list = stateFlowOrders.value.toMutableList()
        list.remove(order)
        stateFlowOrders.value = list
        if (list.isEmpty()) {
            allOrderAreCleared = true
        }
        viewModelScope.launch(IO) {
            val list: MutableList<Pair<Int, SaverOrderUI>> = mutableOrderExpInStateFlow.value
            val saverOrder = list.firstOrNull {
                it.first == order.id
            }
            if (saverOrder != null) {
                list.remove(saverOrder)
                mutableOrderExpInStateFlow.value = list
            }
        }

    }


    fun retrieveAllOrder() {
        if (mutableOrderExpInStateFlow.value.isEmpty()) {
            isLoading = true
            allOrderAreCleared = false
            viewModelScope.launch {
                retrieveAllOrderUseCase.invoke().collect { response ->
                    mutableFlowOrders.value = response
                    isLoading = false
                }
            }
        }

    }

}