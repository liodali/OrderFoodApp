package dali.hamza.orderfoodapp.ui.compose.page

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import dali.hamza.domain.models.Category
import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.Ingredient
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.orderfoodapp.ui.MainActivity
import dali.hamza.orderfoodapp.ui.compose.component.ItemIngredient
import dali.hamza.orderfoodapp.ui.compose.component.LoadingComponent
import dali.hamza.orderfoodapp.ui.compose.theme.Gray300
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun IngredientsCompose(retrieve: () -> Unit) {
    val ingredientViewModel = MainActivity.ingredientsViewModelComposition.current.getVM()
    val composableScope = rememberCoroutineScope()
    val currentData by rememberUpdatedState(newValue = retrieve)
    LaunchedEffect(key1 = "ingredientCategories") {
        composableScope.launch(Dispatchers.IO) {
            currentData()
        }
    }
    Scaffold {
        when (ingredientViewModel.isLoadingCategories) {
            true -> {
                LoadingComponent()
            }
            else -> {
                val categoriesResponse = ingredientViewModel.categories().collectAsState()
                @OptIn(ExperimentalPagerApi::class)
                BodyScaffoldIngredientPage(
                    (categoriesResponse.value!! as SuccessResponse<List<Category>>).data
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun BodyScaffoldIngredientPage(listCategories: List<Category>) {
    val pagerState = rememberPagerState(pageCount = listCategories.size)
    val configuration = LocalConfiguration.current
    val fractionTabs = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 0.8f
        else -> 1f
    }
    Column() {
        Box(Modifier.fillMaxWidth(fraction = fractionTabs)) {
            TabsCategories(
                tabs = listCategories,
                pagerState = pagerState,
            )
        }
        HorizontalPager(
            state = pagerState,
            itemSpacing = 0.dp,
            dragEnabled = false,
        ) { page ->
            IngredientByCategoriesCompose(listCategories[page])
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IngredientByCategoriesCompose(category: Category) {
    val ingredientViewModel = MainActivity.ingredientsViewModelComposition.current.getVM()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = category) {
        scope.launch {
            ingredientViewModel.retrieveIngredientsByCategory(
                category.id
            )
        }
    }
    DisposableEffect(key1 = "disposable-ingredient-${category}") {
        onDispose {
            scope.cancel()
        }
    }
    when {
        ingredientViewModel.isLoadingIngredients -> {
            LoadingComponent()
        }
        else -> {
            BodyIngredientsCompose()
        }
    }
}

@Composable
fun BodyIngredientsCompose() {
    val ingredientViewModel = MainActivity.ingredientsViewModelComposition.current.getVM()
    val ingredientStateResponse = ingredientViewModel.ingredients().collectAsState()
    when (ingredientStateResponse.value) {
        is ErrorResponse -> {

        }
        else -> {
            val list =
                (ingredientStateResponse.value as SuccessResponse<List<Ingredient>>).data
            @OptIn(ExperimentalFoundationApi::class)
            ListIngredientsCompose(list)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ListIngredientsCompose(ingredients: List<Ingredient>) {
    val configuration = LocalConfiguration.current
    val itemCount = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 3
        else -> 2
    }
    LazyVerticalGrid(cells = GridCells.Fixed(itemCount)) {
        items(ingredients.size) { index ->
            ItemIngredient(ingredients[index])
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsCategories(
    tabs: List<Category>,
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = MaterialTheme.colors.primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(tab.category_name) },
                selected = true,//pagerState.currentPage == index,
                modifier = Modifier
                    .padding(0.dp),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}
