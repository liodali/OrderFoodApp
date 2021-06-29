package dali.hamza.orderfoodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.orderfoodapp.model.AppComposition
import dali.hamza.orderfoodapp.ui.compose.theme.FoodAppTheme
import dali.hamza.orderfoodapp.model.Routes.Companion.rememberRoutesNames
import dali.hamza.orderfoodapp.ui.MainActivity.Companion.ingredientsViewModelComposition
import dali.hamza.orderfoodapp.ui.MainActivity.Companion.orderViewModelComposition
import dali.hamza.orderfoodapp.ui.compose.page.IngredientsCompose
import dali.hamza.orderfoodapp.ui.compose.page.OrderComposePage
import dali.hamza.orderfoodapp.viewmodel.IngredientsViewModel
import dali.hamza.orderfoodapp.viewmodel.OrderViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        val orderViewModelComposition =
            compositionLocalOf<AppComposition<OrderViewModel>> { error("No viewModel found!") }
        val ingredientsViewModelComposition =
            compositionLocalOf<AppComposition<IngredientsViewModel>> { error("No viewModel found!") }

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
    val routes = rememberRoutesNames()
    val started = routes.orders
    FoodAppTheme() {
        Scaffold() {
            NavHost(navController, startDestination = "orders") {
                composable(started) { _ ->
                    val orderViewModel = hiltViewModel<OrderViewModel>()
                    CompositionLocalProvider(
                        orderViewModelComposition provides AppComposition(
                            orderViewModel,
                            navController
                        )
                    ) {
                        OrderComposePage(retrieve = {
                            orderViewModel.retrieveAllOrder()
                        })
                    }
                }
                composable(routes.ingredients) { _ ->
                    val ingredientsViewModel = hiltViewModel<IngredientsViewModel>()
                    CompositionLocalProvider(
                        ingredientsViewModelComposition provides AppComposition(
                            ingredientsViewModel,
                            navController
                        )
                    ) {
                        IngredientsCompose() {
                            ingredientsViewModel.isLoadingCategories = true
                            ingredientsViewModel.retrieveCategories()
                        }
                    }
                }

            }
        }
    }
}