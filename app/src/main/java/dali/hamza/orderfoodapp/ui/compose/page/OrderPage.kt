package dali.hamza.orderfoodapp.ui.compose.page

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.paging.compose.items

@Composable
fun OrderComposePage() {
    val configuration = LocalConfiguration.current
    Scaffold() {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                LazyRow() {
                    items(5) {

                    }
                }
            }
            else -> {
                @OptIn(ExperimentalFoundationApi::class)
                LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                    items(5) {

                    }
                }
            }
        }
    }
}