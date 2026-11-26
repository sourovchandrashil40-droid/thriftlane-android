package com.thriftlane.ui.list

import com.thriftlane.data.Item
import com.thriftlane.experiment.LayoutVariant

data class ItemListUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val items: List<Item> = emptyList(),
    val query: String = "",
    val favoriteIds: Set<Int> = emptySet(),
    val showFavoritesOnly: Boolean = false,
    val layout: LayoutVariant = LayoutVariant.LIST,
) {
    val visibleItems: List<Item>
        get() {
            val q = query.trim()
            return items.filter { item ->
                (!showFavoritesOnly || item.id in favoriteIds) &&
                    (q.isEmpty() ||
                        item.title.contains(q, ignoreCase = true) ||
                        item.brand.contains(q, ignoreCase = true))
            }
        }
}
