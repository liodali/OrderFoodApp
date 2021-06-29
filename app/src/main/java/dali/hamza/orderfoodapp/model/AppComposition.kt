package dali.hamza.orderfoodapp.model

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dali.hamza.orderfoodapp.R

@Stable
class AppComposition<T : ViewModel>(
    private val viewModel: T,
    private val navController: NavController
) {
    fun getVM() = viewModel
    fun getController() = navController
}


