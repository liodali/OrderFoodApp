package dali.hamza.orderfoodapp.ui.compose.page

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dali.hamza.domain.Order
import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.ui.MainActivity.Companion.orderViewModelComposition
import dali.hamza.orderfoodapp.ui.compose.component.ItemOrderCompose
import dali.hamza.orderfoodapp.ui.compose.component.LoadingComponent
import dali.hamza.orderfoodapp.ui.compose.theme.Gray300
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Composable
fun OrderComposePage() {
    val orderViewModel = orderViewModelComposition.current
    val composableScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "Orders") {
        composableScope.launch(IO) {
            orderViewModel.isLoading = true
            orderViewModel.retrieveAllOrder()
        }
    }
    Scaffold(

        backgroundColor = when (isSystemInDarkTheme()) {
            true -> MaterialTheme.colors.background
            false -> Gray300
        }
    ) {
        when (orderViewModel.isLoading) {
            true -> {
                LoadingComponent()
            }
            else -> {
                BodyScaffoldOrderPage()
            }
        }

    }
}

@Composable
fun BodyScaffoldOrderPage() {
    val ordersResponseState = orderViewModelComposition.current.getAllOrders().collectAsState()
    when (ordersResponseState.value) {
        is ErrorResponse -> {

        }
        is SuccessResponse<*> -> {
            val orderResponse = ordersResponseState.value!! as SuccessResponse<List<Order>>
            ListOrders(orderResponse.data)
        }
    }
}

@Composable
fun ListOrders(orders: List<Order>) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LazyRow() {
                items(orders) { order ->
                    Box(
                        Modifier
                            .width(320.dp)
                            .fillMaxHeight()
                    ) {
                        ItemOrderCompose(itemOrder = order)
                    }
                }
            }
        }
        else -> {
            @OptIn(ExperimentalFoundationApi::class)
            LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                items(orders) { order ->
                    ItemOrderCompose(itemOrder = order)
                }
            }
        }
    }

}