package com.thriftlane.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.thriftlane.data.Item
import com.thriftlane.experiment.LayoutVariant

/** Stateless: everything comes in through [state], everything goes out through callbacks. */
@Composable
fun ItemListScreen(
    state: ItemListUiState,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {},
    onToggleFavorite: (Int) -> Unit = {},
    onToggleFavoritesOnly: () -> Unit = {},
    onItemClick: (Int) -> Unit = {},
    onRetry: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("ThriftLane", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.query,
            onValueChange = onQueryChange,
            label = { Text("Search items or brands") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("search_field"),
        )
        FilterChip(
            selected = state.showFavoritesOnly,
            onClick = onToggleFavoritesOnly,
            label = { Text("Favourites only") },
            modifier = Modifier.padding(vertical = 8.dp).testTag("favorites_filter"),
        )

        val items = state.visibleItems
        when {
            state.isLoading -> CenteredBox {
                CircularProgressIndicator(Modifier.testTag("loading"))
            }
            state.error != null -> CenteredBox {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.error, modifier = Modifier.testTag("error"))
                    Button(onClick = onRetry, modifier = Modifier.testTag("retry")) { Text("Retry") }
                }
            }
            items.isEmpty() -> CenteredBox {
                Text("No items found", modifier = Modifier.testTag("empty_state"))
            }
            state.layout == LayoutVariant.GRID -> ItemGrid(items, state.favoriteIds, onItemClick, onToggleFavorite)
            else -> ItemColumn(items, state.favoriteIds, onItemClick, onToggleFavorite)
        }
    }
}

@Composable
private fun ItemColumn(
    items: List<Item>,
    favoriteIds: Set<Int>,
    onItemClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize().testTag("item_list"),
    ) {
        items(items, key = { it.id }) { item ->
            ItemCard(
                item = item,
                isFavorite = item.id in favoriteIds,
                onClick = { onItemClick(item.id) },
                onFavoriteClick = { onToggleFavorite(item.id) },
            )
        }
    }
}

@Composable
private fun ItemGrid(
    items: List<Item>,
    favoriteIds: Set<Int>,
    onItemClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize().testTag("item_list"),
    ) {
        items(items, key = { it.id }) { item ->
            ItemCard(
                item = item,
                isFavorite = item.id in favoriteIds,
                onClick = { onItemClick(item.id) },
                onFavoriteClick = { onToggleFavorite(item.id) },
            )
        }
    }
}

@Composable
private fun CenteredBox(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { content() }
}
