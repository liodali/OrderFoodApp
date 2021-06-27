package dali.hamza.orderfoodapp.ui.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dali.hamza.domain.Addon

@Composable
fun ListAddon(addons: List<Addon>) {

    when (addons.isEmpty()) {
        true -> {
            Text("no addons")
        }
        else -> {
            val scrollState = rememberScrollState()

            Column(
                Modifier.verticalScroll(scrollState)
            ) {
                Text("addon1")
                Text("addon1")
                Text("addon1")
            }
        }
    }

}