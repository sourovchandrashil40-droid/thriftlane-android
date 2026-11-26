package com.thriftlane.data

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ItemTest {

    @Test
    fun `formats price in euros with two decimals`() {
        assertEquals("€8.50", sampleItems[1].formattedPrice)
        assertEquals("€25.00", sampleItems[0].formattedPrice)
    }

    @Test
    fun `repository returns item by id or null`() = runTest {
        val repository = FakeItemRepository(latencyMs = 0)

        assertEquals("Wool scarf", repository.getItem(2)?.title)
        assertNull(repository.getItem(999))
    }
}
