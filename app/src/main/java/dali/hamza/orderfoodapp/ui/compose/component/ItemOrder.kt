package dali.hamza.orderfoodapp.ui.compose.component

import android.os.CountDownTimer
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.core.common.DateExpiration
import dali.hamza.core.common.DateManager
import dali.hamza.domain.Order
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.model.OrderTimeUI
import dali.hamza.orderfoodapp.model.UIOrder
import dali.hamza.orderfoodapp.ui.MainActivity
import dali.hamza.orderfoodapp.ui.compose.theme.Gray300
import dali.hamza.orderfoodapp.ui.compose.theme.Gray400
import dali.hamza.orderfoodapp.ui.compose.theme.Gray600
import dali.hamza.orderfoodapp.ui.compose.theme.Gray700
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import android.media.MediaPlayer

import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.ui.platform.LocalContext


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
fun rememberTimerOrderCompose(uiOrder: UIOrder, playSound: () -> Unit): OrderTimeUI {
    val startTime = uiOrder.dateExpirationIn.seconds.toLong() * 1000
    var orderExpInTimer by remember {
        mutableStateOf(
            OrderTimeUI(
                uiOrder.dateExpirationIn,
                null
            )
        )
    }

    val scope = rememberCoroutineScope()
    val timer: CountDownTimer by rememberUpdatedState(newValue = object :
        CountDownTimer(startTime, 1000) {
        override fun onTick(millisecs: Long) {
            val seconds = millisecs / 1000
            val minutes = seconds / 60
            orderExpInTimer = orderExpInTimer.copy(
                dateExpiration = DateExpiration(
                    0, 0,
                    minutes = minutes.toInt(),
                    seconds = seconds.toInt()
                ),
                timer = this
            )
            val alertDate = DateManager.format.parse(uiOrder.order.alertedAt)
            val nowCET = DateManager.now()

            if (nowCET.time == alertDate.time) {
                playSound()
            }

        }

        override fun onFinish() {
            //...countdown completed
            this.cancel()
        }
    })

    LaunchedEffect(key1 = "${uiOrder.order.id * DateManager.now().time}") {
        scope.launch {
            timer.start()
        }

    }
    DisposableEffect("${uiOrder.order.id * DateManager.now().time}") {

        onDispose {
            scope.cancel()
        }
    }

    return orderExpInTimer
}

@Composable
fun ItemOrderCompose(itemOrder: Order, playSound: () -> Unit) {
    val diff = DateManager.difference2Date(
        d2 = DateManager.format.parse(itemOrder.expiredAt),
        d1 = DateManager.format.parse(itemOrder.createdAt)
    )
    val expInWithTimer = rememberTimerOrderCompose(
        uiOrder = UIOrder(
            order = itemOrder,
            dateExpirationIn = diff
        ),
        playSound = playSound
    )
    //val expIn = viewModel.getExpirationIn().collectAsState()
    val uiOrder = UIOrder(
        order = itemOrder,
        isExpired = expInWithTimer.dateExpiration.seconds == 0,
        dateExpirationIn = expInWithTimer.dateExpiration
    )


    Card(
        backgroundColor = Gray400,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
    ) {
        Column() {
            HeaderItemOrderCompose(uiOrder, diff)
            BodyItemOrderCompose(
                uiOrder.order,
                isExpired = uiOrder.isExpired,
                timer = expInWithTimer.timer
            )
        }
    }
}

@Composable
fun HeaderItemOrderCompose(
    itemOrder: UIOrder,
    startDiff: DateExpiration,
) {
    val progress = itemOrder.dateExpirationIn.seconds.toFloat() / startDiff.seconds.toFloat()
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
            Modifier
                .weight(
                    weight = 0.7f,
                    fill = true
                )
                .wrapContentHeight(align = Alignment.Bottom)
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)
                    .wrapContentHeight(align = Alignment.Bottom)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.auto_reject_label),
                    fontSize = 12.sp,
                    color = Gray600,
                    textAlign = TextAlign.End,
                    modifier = Modifier.wrapContentHeight(Alignment.Bottom)
                )
                WidthDividerSpacer(
                    width = 2.dp,
                    color = Color.Transparent
                )
                Text(
                    text = showTimeDiffOrder(itemOrder.dateExpirationIn),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            SegmentedProgressIndicator(
                progress = if (progress < 1) 1 - progress else progress - 1,
                progressHeight = 4.dp,
                numberOfSegments = 4,
                segmentGap = 8.dp,
                backgroundColor = MaterialTheme.colors.primary,
                color = Gray700,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.Bottom)
                    .padding(horizontal = 5.dp, vertical = 8.dp)
                //.rotate(180f)

            )
        }
    }
}


@Composable
fun BodyItemOrderCompose(
    itemOrder: Order,
    isExpired: Boolean = false,
    timer: CountDownTimer? = null,
) {
    val viewModel = MainActivity.orderViewModelComposition.current
    val context = LocalContext.current

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
                    onClick = {
                        if (!isExpired) {
                            timer!!.cancel()
                            val notification: Uri =
                                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                            val player: MediaPlayer = MediaPlayer.create(context, notification)
                            player.start()
                        }
                        viewModel.removeOrder(itemOrder)


                    },
                    enabled = timer != null
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
