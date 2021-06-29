package dali.hamza.orderfoodapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dali.hamza.core.interactor.RetrieveAllCategoriesUseCase
import dali.hamza.core.interactor.RetrieveIngredientsUseCase
import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.IResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private val retrieveAllCategoriesUseCase: RetrieveAllCategoriesUseCase,
    private val retrieveIngredientsUseCase: RetrieveIngredientsUseCase
) : ViewModel() {


    var isLoadingCategories: Boolean by mutableStateOf(true)
    var isLoadingIngredients: Boolean by mutableStateOf(true)


    private val mutableFlowCategories: MutableStateFlow<IResponse?> = MutableStateFlow(null)
    private val mutableFlowIngredients: MutableStateFlow<IResponse?> = MutableStateFlow(null)

    fun categories(): StateFlow<IResponse?> = mutableFlowCategories
    fun ingredients(): StateFlow<IResponse?> = mutableFlowIngredients


    fun retrieveCategories() {
        isLoadingCategories = true
        viewModelScope.launch(IO) {
            retrieveAllCategoriesUseCase.invoke()
                .catch {
                    mutableFlowCategories.value = ErrorResponse("Error get Categories")
                    isLoadingCategories = false
                }
                .collect { response ->
                    mutableFlowCategories.value = response
                    isLoadingCategories = false
                }
        }
    }

     fun retrieveIngredientsByCategory(idCategory: Int) {
        isLoadingIngredients = true
        viewModelScope.launch(IO) {
            retrieveIngredientsUseCase.invoke(idCategory)
                .catch {
                    mutableFlowIngredients.value = ErrorResponse("Error get ingredients by  Categories")
                    isLoadingIngredients = false
                }
                .collect { response ->
                    mutableFlowIngredients.value = response
                    isLoadingIngredients = false
                }
        }
    }


}