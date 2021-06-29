package dali.hamza.orderfoodapp.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import dali.hamza.orderfoodapp.R

class Routes {

    internal var orders = ""
    internal var ingredients = ""

    companion object {
        @Composable
        fun rememberRoutesNames(): Routes {
            val routes by remember {
                mutableStateOf(Routes())
            }
            routes.orders = stringResource(id = R.string.order_page)
            routes.ingredients = stringResource(id = R.string.ingredient_page)
            return routes
        }
    }
}