package dali.hamza.orderfoodapp.ui.compose.page

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
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
fun OrderComposePage(retrieve: () -> Unit) {
    val orderViewModel = orderViewModelComposition.current
    val composableScope = rememberCoroutineScope()
    val currentData by rememberUpdatedState(newValue = retrieve)
    LaunchedEffect(key1 = "Orders") {
        composableScope.launch(IO) {
            currentData()
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
            val horizontalScrollState = rememberScrollState()
            Row(
                Modifier.horizontalScroll(horizontalScrollState)
            ) {
                for (order in orders) {
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
            val verticalScrollState = rememberScrollState()
            Column(
                Modifier.verticalScroll(verticalScrollState)
            ) {
                for (order in orders) {
                    Box(
                        Modifier
                            .height(378.dp)
                            .fillMaxWidth()
                    ) {
                        ItemOrderCompose(itemOrder = order)
                    }
                }
            }
        }
    }

}