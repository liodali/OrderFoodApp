package dali.hamza.orderfoodapp.ui.compose.component

import android.os.CountDownTimer
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.core.common.DateExpiration
import dali.hamza.core.common.DateManager
import dali.hamza.domain.Order
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.model.UIOrder
import dali.hamza.orderfoodapp.ui.compose.theme.Gray300
import dali.hamza.orderfoodapp.ui.compose.theme.Gray400
import dali.hamza.orderfoodapp.ui.compose.theme.Gray600
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

fun showTimeDiffOrder(diffExp: DateExpiration): String {
    return when {
        diffExp.seconds >= 60 -> {
            return " ${diffExp.minutes + 1}min"
        }
        diffExp.seconds in 1..59 -> {
            " ${diffExp.seconds}s"
        }
        else -> {
            "finish"
        }
    }

}


@Composable
fun rememberTimerOrderCompose(uiOrder: UIOrder): DateExpiration {

    var expIn by remember {
        mutableStateOf(uiOrder.dateExpirationIn)
    }
    val startTime = uiOrder.dateExpirationIn.seconds.toLong() * 1000

    val scope = rememberCoroutineScope()
    val timer = object : CountDownTimer(startTime, 1000) {
        override fun onTick(millisecs: Long) {
            val seconds = millisecs / 1000
            val minutes = seconds / 60
            expIn =
                DateExpiration(
                    0, 0,
                    minutes = minutes.toInt(),
                    seconds = seconds.toInt()
                )
        }

        override fun onFinish() {
            //...countdown completed
            this.cancel()
        }
    }
    LaunchedEffect(key1 = "${uiOrder.order.id}") {

        scope.launch {
            timer.start()
        }
    }
    DisposableEffect("${uiOrder.order.id}") {

        onDispose {
            scope.cancel()
        }
    }

    return expIn
}

fun calculateDiff(uiOrder: UIOrder): UIOrder {
    var sUiOrder = uiOrder.copy()
    val expiredDate = DateManager.adjustDateWithCurrentCET(uiOrder.order.expiredAt)
    when (DateManager.now().before(expiredDate)) {
        true -> {
            val diffExpirationDate = DateManager.difference2Date(
                d2 = expiredDate,
                d1 = DateManager.now()
            )
            sUiOrder = uiOrder.copy(
                isExpired = diffExpirationDate.minutes == 0 && diffExpirationDate.seconds == 0,
                dateExpirationIn = diffExpirationDate
            )
        }
        else -> {
            sUiOrder = sUiOrder.copy(
                isExpired = true
            )
        }
    }
    return sUiOrder.copy()
}

@Composable
fun ItemOrderCompose(itemOrder: Order) {
    val diff = DateManager.difference2Date(
        d2 = DateManager.format.parse(itemOrder.expiredAt),
        d1 = DateManager.format.parse(itemOrder.createdAt)
    )
    val expIn = rememberTimerOrderCompose(
        uiOrder = UIOrder(
            order = itemOrder,
            dateExpirationIn = diff
        )
    )
    //val expIn = viewModel.getExpirationIn().collectAsState()
    val uiOrder = UIOrder(
        order = itemOrder,
        isExpired = expIn.seconds == 0,
        dateExpirationIn = expIn
    )


    Card(
        backgroundColor = Gray400,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
    ) {
        Column() {
            HeaderItemOrderCompose(uiOrder)
            BodyItemOrderCompose(uiOrder.order, isExpired = uiOrder.isExpired)
        }
    }
}

@Composable
fun HeaderItemOrderCompose(itemOrder: UIOrder) {
    Row() {
        Column(
            Modifier.weight(weight = 0.4f),
        ) {
            TitleText(
                text = "#${itemOrder.order.id}",
                modifier = Modifier.padding(start = 5.dp)
            )
            Text(
                text = "at ${DateManager.formatToTime(itemOrder.order.expiredAt)}",
                fontSize = 12.sp,
                color = Gray600,
                modifier = Modifier.padding(start = 5.dp)

            )
        }
        Column(
            Modifier.weight(
                weight = 0.7f,
                fill = true
            )
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 12.sp,
                            color = Gray600
                        )
                    ) {
                        append(stringResource(id = R.string.auto_reject_label))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(showTimeDiffOrder(itemOrder.dateExpirationIn))
                    }
                },
            )
        }
    }
}


@Composable
fun BodyItemOrderCompose(itemOrder: Order, isExpired: Boolean = false) {
    Card(
        elevation = 0.dp,
        modifier = Modifier.fillMaxHeight(),
        backgroundColor = when (isSystemInDarkTheme()) {
            true -> Gray300
            else -> MaterialTheme.colors.background
        }
    ) {
        Column {
            HeightDividerSpacer(
                height = 1.dp,
                color = when (isSystemInDarkTheme()) {
                    true -> Color.Black
                    else -> Color.Gray
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.15f)
                    .wrapContentWidth(align = Alignment.End)
                    .padding(vertical = 3.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(24.dp),
                    onClick = { /*TODO*/ }
                ) {
                    when (isExpired) {
                        true -> Text("Delete")

                        else -> Text("Accept")

                    }
                }
            }
            HeightDividerSpacer(
                height = 1.dp,
                color = when (isSystemInDarkTheme()) {
                    true -> Color.Black
                    else -> Color.Gray
                }
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                        end = 5.dp
                    )
                    .weight(0.8f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    ListAddon(itemOrder.addon)
                }
                HeightDividerSpacer(
                    height = 1.dp,
                    color = when (isSystemInDarkTheme()) {
                        true -> Color.Black
                        else -> Color.Gray
                    }
                )
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .weight(0.15f)

                ) {
                    Text("items ${itemOrder.addon.size}")
                }

            }
        }
    }
}
