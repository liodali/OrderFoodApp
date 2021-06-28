package dali.hamza.orderfoodapp.ui.compose.page

import android.content.res.Configuration
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.domain.Order
import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.ui.MainActivity.Companion.orderViewModelComposition
import dali.hamza.orderfoodapp.ui.compose.component.EmptyInformation
import dali.hamza.orderfoodapp.ui.compose.component.ItemOrderCompose
import dali.hamza.orderfoodapp.ui.compose.component.LoadingComponent
import dali.hamza.orderfoodapp.ui.compose.theme.Gray300
import dali.hamza.orderfoodapp.ui.compose.theme.Gray700
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
    val viewModel = orderViewModelComposition.current
    val ordersResponseState = viewModel.getAllOrders().collectAsState()
    when (ordersResponseState.value) {
        is ErrorResponse -> {

        }
        is SuccessResponse<*> -> {
            val orderResponse = ordersResponseState.value!! as SuccessResponse<List<Order>>
            viewModel.init(orderResponse.data)
            ListOrdersCompose()
        }
    }
}

@Composable
fun ListOrdersCompose() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val viewModel = orderViewModelComposition.current
    val stateOrders = viewModel.orders().collectAsState()
    val listOrders = stateOrders.value
    when {
        listOrders.isNotEmpty() -> {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    val horizontalScrollState = rememberScrollState()
                    Row(
                        Modifier.horizontalScroll(horizontalScrollState)
                    ) {
                        for (order in listOrders) {
                            Box(
                                Modifier
                                    .width(320.dp)
                                    .fillMaxHeight(),

                                ) {
                                ItemOrderCompose(
                                    itemOrder = order,
                                ) {
                                    val notification: Uri =
                                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                                    val player: MediaPlayer =
                                        MediaPlayer.create(context, notification)
                                    player.start()
                                }
                            }
                        }
                    }
                }
                else -> {
                    val verticalScrollState = rememberScrollState()
                    Column(
                        Modifier.verticalScroll(verticalScrollState)
                    ) {
                        for (order in listOrders) {
                            Box(
                                Modifier
                                    .height(378.dp)
                                    .fillMaxWidth()
                            ) {
                                ItemOrderCompose(itemOrder = order) {
                                    val notification: Uri =
                                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                                    val player: MediaPlayer =
                                        MediaPlayer.create(context, notification)
                                    player.start()
                                }
                            }
                        }
                    }
                }
            }
        }
        else -> {
            EmptyInformation(
                painter = painterResource(id = R.drawable.ic_baseline_food_bank_24),
                text = stringResource(id = R.string.no_order)
            )
        }
    }


}