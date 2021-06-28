package dali.hamza.orderfoodapp.ui.compose.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.domain.Addon
import dali.hamza.orderfoodapp.ui.compose.theme.Gray600

@Composable
fun ListAddon(addons: List<Addon>) {

    when (addons.isEmpty()) {
        true -> {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
            ) {
                Text(
                    "no addons",
                    Modifier
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
        else -> {
            LazyColumn() {
                items(addons) { addon ->
                    ItemAddon(addon = addon)
                }
            }
        }
    }

}

@Composable
fun ItemAddon(addon: Addon) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 8.sp,
                        color = Gray600
                    )
                ) {
                    append("x")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("${addon.quantity} ${addon.title}")
                }
            }
        )
    }
}