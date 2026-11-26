package com.thriftlane.experiment

import org.junit.Assert.assertEquals
import org.junit.Test

class ExperimentServiceTest {

    @Test
    fun `same user always gets the same variant`() {
        assertEquals(
            ExperimentService("user-42").layoutVariant(),
            ExperimentService("user-42").layoutVariant(),
        )
    }

    @Test
    fun `forced variant overrides assignment`() {
        assertEquals(LayoutVariant.GRID, ExperimentService("any", LayoutVariant.GRID).layoutVariant())
        assertEquals(LayoutVariant.LIST, ExperimentService("any", LayoutVariant.LIST).layoutVariant())
    }

    @Test
    fun `both variants are used across many users`() {
        val variants = (1..100).map { ExperimentService("user-$it").layoutVariant() }.toSet()

        assertEquals(LayoutVariant.entries.toSet(), variants)
    }
}
