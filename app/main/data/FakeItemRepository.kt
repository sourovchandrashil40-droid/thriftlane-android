package com.thriftlane.data

import kotlinx.coroutines.delay

/** Simulates a network-backed repository with local sample data. */
class FakeItemRepository(
    private val latencyMs: Long = 600,
) : ItemRepository {

    override suspend fun getItems(): List<Item> {
        delay(latencyMs)
        return sampleItems
    }

    override suspend fun getItem(id: Int): Item? {
        delay(latencyMs)
        return sampleItems.firstOrNull { it.id == id }
    }
}

val sampleItems: List<Item> = listOf(
    Item(1, "Blue denim jacket", "Levi's", "M", 25.00, "Very good", "Classic trucker jacket, lightly worn. No stains or tears."),
    Item(2, "Wool scarf", "Zara", "One size", 8.50, "Good", "Soft grey wool scarf, perfect for winter."),
    Item(3, "Running shoes", "Nike", "42", 30.00, "Good", "Air Zoom running shoes, around 200 km of use."),
    Item(4, "Black hoodie", "Adidas", "L", 18.00, "Very good", "Heavy cotton hoodie with front pocket."),
    Item(5, "Floral summer dress", "H&M", "S", 12.00, "New with tags", "Light dress, never worn, tags still attached."),
    Item(6, "Leather backpack", "Fjällräven", "One size", 45.00, "Good", "Durable backpack with laptop compartment."),
    Item(7, "Slim fit jeans", "Levi's", "32/32", 22.00, "Satisfactory", "Some fading at the knees, otherwise solid."),
    Item(8, "Knitted sweater", "Zara", "M", 15.00, "Very good", "Chunky cream knit sweater."),
)
