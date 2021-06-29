package dali.hamza.orderfoodapp.ui.compose.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.ui.compose.theme.Gray700

@Composable
fun TitleText(
    text: String,
    textSize: TextUnit = 18.sp,
    textColor: Color = Color.Black,
    modifier: Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1.copy(
            color = textColor,
            fontSize = textSize,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier
    )
}

@Composable
fun LoadingComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(stringResource(id = R.string.loadingText))

    }
}

@Composable
fun EmptyInformation(
    painter: Painter,
    text: String,
) {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .width(56.dp)
                .height(56.dp),
            tint = when {
                isSystemInDarkTheme() -> MaterialTheme.colors.onBackground
                else -> Gray700
            }
        )
        Text(
            text,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}

@Composable
fun rememberRoutesNames(): List<String> {
    return listOf(
        stringResource(id = R.string.order_page),
        stringResource(id = R.string.ingredient_page)
    )
}