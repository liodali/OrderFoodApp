package dali.hamza.orderfoodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.orderfoodapp.ui.compose.theme.FoodAppTheme
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.ui.MainActivity.Companion.orderViewModelComposition
import dali.hamza.orderfoodapp.ui.compose.page.OrderComposePage
import dali.hamza.orderfoodapp.ui.compose.theme.FoodAppTheme
import dali.hamza.orderfoodapp.viewmodel.OrderViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        val orderViewModelComposition =
            compositionLocalOf<OrderViewModel> { error("No viewModel found!") }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val started = stringResource(id = R.string.order_page)
    FoodAppTheme() {
        NavHost(navController, startDestination = started) {
            composable(started) { backStackEntry ->
                val orderViewModel = hiltViewModel<OrderViewModel>()
                CompositionLocalProvider(orderViewModelComposition provides orderViewModel) {
                    OrderComposePage(retrieve = {
                        orderViewModel.isLoading = true
                        orderViewModel.retrieveAllOrder()
                    })
                }
            }

        }
    }
}