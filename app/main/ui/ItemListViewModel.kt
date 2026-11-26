package com.thriftlane.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thriftlane.analytics.Analytics
import com.thriftlane.data.ItemRepository
import com.thriftlane.experiment.ExperimentService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val repository: ItemRepository,
    experiments: ExperimentService,
    private val analytics: Analytics,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemListUiState(layout = experiments.layoutVariant()))
    val uiState: StateFlow<ItemListUiState> = _uiState.asStateFlow()

    init {
        analytics.track(
            "experiment_exposure",
            mapOf(
                "experiment" to ExperimentService.LAYOUT_EXPERIMENT,
                "variant" to _uiState.value.layout.name,
            ),
        )
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val items = repository.getItems()
                _uiState.update { it.copy(isLoading = false, items = items) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Could not load items") }
            }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun onToggleFavorite(itemId: Int) {
        _uiState.update { state ->
            val favorites = if (itemId in state.favoriteIds) {
                state.favoriteIds - itemId
            } else {
                state.favoriteIds + itemId
            }
            state.copy(favoriteIds = favorites)
        }
        analytics.track("favorite_toggled", mapOf("item_id" to itemId.toString()))
    }

    fun onToggleFavoritesOnly() {
        _uiState.update { it.copy(showFavoritesOnly = !it.showFavoritesOnly) }
    }
}
