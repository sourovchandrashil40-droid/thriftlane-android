package com.thriftlane.ui.list

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.thriftlane.data.sampleItems
import com.thriftlane.experiment.LayoutVariant
import com.thriftlane.ui.theme.ThriftLaneTheme
import org.junit.Rule
import org.junit.Test

/**
 * Record golden images:  ./gradlew recordPaparazziDebug
 * Verify against them:   ./gradlew verifyPaparazziDebug
 */
class ItemSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar",
    )

    private val loaded = ItemListUiState(isLoading = false, items = sampleItems, favoriteIds = setOf(1))

    @Test
    fun itemCard() {
        paparazzi.snapshot {
            ThriftLaneTheme(darkTheme = false) {
                ItemCard(item = sampleItems.first(), isFavorite = true, onClick = {}, onFavoriteClick = {})
            }
        }
    }

    @Test
    fun listLayout() {
        paparazzi.snapshot {
            ThriftLaneTheme(darkTheme = false) {
                ItemListScreen(state = loaded.copy(layout = LayoutVariant.LIST))
            }
        }
    }

    @Test
    fun gridLayout() {
        paparazzi.snapshot {
            ThriftLaneTheme(darkTheme = false) {
                ItemListScreen(state = loaded.copy(layout = LayoutVariant.GRID))
            }
        }
    }

    @Test
    fun errorState() {
        paparazzi.snapshot {
            ThriftLaneTheme(darkTheme = false) {
                ItemListScreen(state = ItemListUiState(isLoading = false, error = "Could not load items"))
            }
        }
    }
}
