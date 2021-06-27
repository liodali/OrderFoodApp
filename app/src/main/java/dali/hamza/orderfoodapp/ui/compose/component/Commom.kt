package dali.hamza.orderfoodapp.ui.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dali.hamza.orderfoodapp.R

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
