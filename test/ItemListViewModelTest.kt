package com.thriftlane.ui.list

import com.thriftlane.data.ItemRepository
import com.thriftlane.data.sampleItems
import com.thriftlane.experiment.ExperimentService
import com.thriftlane.experiment.LayoutVariant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ItemListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val analytics = FakeAnalytics()

    private fun createViewModel(
        repository: ItemRepository = StubRepository(sampleItems),
        variant: LayoutVariant = LayoutVariant.LIST,
    ) = ItemListViewModel(repository, ExperimentService("user-1", variant), analytics)

    @Test
    fun `loads items on init`() {
        val state = createViewModel().uiState.value

        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(sampleItems, state.items)
    }

    @Test
    fun `search filters by title ignoring case`() {
        val vm = createViewModel()

        vm.onQueryChange("DENIM")

        assertEquals(listOf("Blue denim jacket"), vm.uiState.value.visibleItems.map { it.title })
    }

    @Test
    fun `search also matches brand`() {
        val vm = createViewModel()

        vm.onQueryChange("zara")

        assertEquals(listOf(2, 8), vm.uiState.value.visibleItems.map { it.id })
    }

    @Test
    fun `blank query shows all items`() {
        val vm = createViewModel()

        vm.onQueryChange("   ")

        assertEquals(sampleItems.size, vm.uiState.value.visibleItems.size)
    }

    @Test
    fun `toggling favourite adds and removes id and tracks event`() {
        val vm = createViewModel()

        vm.onToggleFavorite(3)
        assertEquals(setOf(3), vm.uiState.value.favoriteIds)

        vm.onToggleFavorite(3)
        assertTrue(vm.uiState.value.favoriteIds.isEmpty())
        assertEquals(2, analytics.events.count { it.first == "favorite_toggled" })
    }

    @Test
    fun `favourites only filter shows only favourites`() {
        val vm = createViewModel()
        vm.onToggleFavorite(1)
        vm.onToggleFavorite(5)

        vm.onToggleFavoritesOnly()

        assertEquals(listOf(1, 5), vm.uiState.value.visibleItems.map { it.id })
    }

    @Test
    fun `shows error when loading fails and recovers on retry`() {
        val vm = createViewModel(repository = FlakyRepository(sampleItems, failures = 1))
        assertNotNull(vm.uiState.value.error)
        assertTrue(vm.uiState.value.items.isEmpty())

        vm.loadItems()

        assertNull(vm.uiState.value.error)
        assertEquals(sampleItems, vm.uiState.value.items)
    }

    @Test
    fun `uses layout from experiment and tracks exposure`() {
        val vm = createViewModel(variant = LayoutVariant.GRID)

        assertEquals(LayoutVariant.GRID, vm.uiState.value.layout)
        val exposure = analytics.events.single { it.first == "experiment_exposure" }
        assertEquals("GRID", exposure.second["variant"])
    }
}
