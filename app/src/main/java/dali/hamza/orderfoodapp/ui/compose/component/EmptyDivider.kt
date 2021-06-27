package dali.hamza.orderfoodapp.ui.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Composable
fun HeightDividerSpacer(
    height: Dp,
    color: Color = Color.White
) {

    Spacer(
        modifier = Modifier .background(color = color)
            .height(height = height).fillMaxWidth()
    )
}

@Composable
fun WidthDividerSpacer(
    width: Dp,
    color: Color = Color.White
) {

    Spacer(
        modifier = Modifier
            .width(width = width).fillMaxHeight()
            .background(color = color)
    )
}