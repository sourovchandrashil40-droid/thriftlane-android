package com.thriftlane.ui.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.thriftlane.data.sampleItems
import com.thriftlane.experiment.LayoutVariant
import com.thriftlane.ui.theme.ThriftLaneTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ItemListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val loaded = ItemListUiState(isLoading = false, items = sampleItems)

    @Test
    fun showsLoadingIndicator() {
        composeRule.setContent { ThriftLaneTheme { ItemListScreen(ItemListUiState(isLoading = true)) } }

        composeRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun showsItemsInListLayout() {
        composeRule.setContent { ThriftLaneTheme { ItemListScreen(loaded) } }

        composeRule.onNodeWithText("Blue denim jacket").assertIsDisplayed()
    }

    @Test
    fun showsItemsInGridLayout() {
        composeRule.setContent {
            ThriftLaneTheme { ItemListScreen(loaded.copy(layout = LayoutVariant.GRID)) }
        }

        composeRule.onNodeWithText("Blue denim jacket").assertIsDisplayed()
    }

    @Test
    fun showsEmptyStateWhenNothingMatches() {
        composeRule.setContent {
            ThriftLaneTheme { ItemListScreen(loaded.copy(query = "zzz")) }
        }

        composeRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun errorStateRetryButtonCallsBack() {
        var retried = false
        composeRule.setContent {
            ThriftLaneTheme {
                ItemListScreen(ItemListUiState(isLoading = false, error = "Could not load items"), onRetry = { retried = true })
            }
        }

        composeRule.onNodeWithTag("retry").performClick()

        assertEquals(true, retried)
    }

    @Test
    fun tappingHeartReportsItemId() {
        var toggled: Int? = null
        composeRule.setContent {
            ThriftLaneTheme { ItemListScreen(loaded, onToggleFavorite = { toggled = it }) }
        }

        composeRule.onNodeWithTag("favorite_1").performClick()

        assertEquals(1, toggled)
    }

    @Test
    fun tappingCardReportsItemId() {
        var clicked: Int? = null
        composeRule.setContent {
            ThriftLaneTheme { ItemListScreen(loaded, onItemClick = { clicked = it }) }
        }

        composeRule.onNodeWithTag("item_card_2").performClick()

        assertEquals(2, clicked)
    }

    @Test
    fun typingInSearchReportsQuery() {
        var query = ""
        composeRule.setContent {
            ThriftLaneTheme { ItemListScreen(loaded, onQueryChange = { query = it }) }
        }

        composeRule.onNodeWithTag("search_field").performTextInput("denim")

        assertEquals("denim", query)
    }
}
