package dali.hamza.orderfoodapp.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.domain.models.Ingredient
import dali.hamza.orderfoodapp.R
import dali.hamza.orderfoodapp.ui.compose.theme.Gray400
import dali.hamza.orderfoodapp.ui.compose.theme.Orange

@Composable
fun ItemIngredient(ingredient: Ingredient) {
    Card(
        Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(
            topStart = 8.dp, topEnd = 8.dp
        )
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
//            Box(
//                //modifier = Modifier.fillMaxWidth().height(96.dp)
//            ) {
//                Image(
//                    painter = rememberCoilPainter(
//                        ingredient.image,
//
//                        ),
//                    contentDescription = "",
//                    Modifier.fillMaxWidth()
//                )
//            }
            Image(
                painter = painterResource(id = R.drawable.food3),
                contentDescription = ""
            )
            Text(text = ingredient.name, fontSize = 14.sp)
            Box(
                modifier = Modifier
                    .wrapContentWidth(
                        align = Alignment.CenterHorizontally
                    )
                    .padding(5.dp)
            ) {
                QuantityIngredient(ingredient.quantity)

            }
        }
    }
}

@Composable
fun QuantityIngredient(quantity: Int) {
    Card(
        backgroundColor = when {
            quantity > 0 -> Orange
            else -> Gray400
        },
        modifier = Modifier
            .width(128.dp)
            .wrapContentWidth(
                align = Alignment.CenterHorizontally
            )
            .wrapContentHeight(
                align = Alignment.CenterVertically
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(
                    id = when {
                        quantity > 0 -> R.string.available
                        else -> R.string.not_available
                    }
                ),
                fontSize = 12.sp,
                color = Color.White
            )
            Card(
                backgroundColor = Color.White,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(3.dp)
            ) {
                Text(
                    "$quantity",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .wrapContentWidth(
                            align = Alignment.CenterHorizontally
                        )
                        .wrapContentHeight(
                            align = Alignment.CenterVertically
                        )
                )
            }
        }
    }
}
