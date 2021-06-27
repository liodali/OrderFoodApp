package dali.hamza.orderfoodapp.ui.compose.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.core.common.DateManager
import dali.hamza.domain.Order
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.ui.compose.theme.Gray300
import dali.hamza.orderfoodapp.ui.compose.theme.Gray400
import dali.hamza.orderfoodapp.ui.compose.theme.Gray600
import dali.hamza.orderfoodapp.ui.compose.theme.Yellow500


@Composable
fun ItemOrderCompose(itemOrder: Order) {
    Card(
        backgroundColor = Gray400,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
    ) {
        Column() {
            HeaderItemOrderCompose(itemOrder)
            BodyItemOrderCompose(itemOrder)
        }
    }
}

@Composable
fun HeaderItemOrderCompose(itemOrder: Order) {
    Row() {
        Column(
            Modifier.weight(weight = 0.4f),
        ) {
            TitleText(
                text = "#${itemOrder.id}",
                modifier = Modifier.padding(start = 5.dp)
            )
            Text(
                text = "at ${DateManager.formatToTime(itemOrder.expiredAt)}",
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
                        append(getDiffTime(itemOrder.expiredAt, itemOrder.createdAt))
                    }
                },
            )
        }
    }
}

fun getDiffTime(expired: String, created: String): String {
    val dateExpiration = DateManager.difference2Date(
        d2 = DateManager.format.parse(expired),
        d1 = DateManager.format.parse(created)
    )
    return when {
        dateExpiration.days > 0 -> "  ${dateExpiration.days}days"
        dateExpiration.hours > 0 -> "  ${dateExpiration.hours}hours"
        dateExpiration.minutes > 0 ->"  ${dateExpiration.minutes}min"
        dateExpiration.seconds > 0 ->" ${dateExpiration.seconds}second"
        else -> "undefined"
    }
}

@Composable
fun BodyItemOrderCompose(itemOrder: Order) {
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
                    Text("Accept")
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
