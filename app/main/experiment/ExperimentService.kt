package com.thriftlane.experiment

enum class LayoutVariant { LIST, GRID }

/**
 * Tiny A/B testing helper. A user is deterministically bucketed by their id,
 * so they always see the same variant. [forcedVariant] is for tests and debugging.
 */
class ExperimentService(
    private val userId: String,
    private val forcedVariant: LayoutVariant? = null,
) {
    fun layoutVariant(): LayoutVariant {
        forcedVariant?.let { return it }
        val bucket = (userId.hashCode() and Int.MAX_VALUE) % LayoutVariant.entries.size
        return LayoutVariant.entries[bucket]
    }

    companion object {
        const val LAYOUT_EXPERIMENT = "item_list_layout"
    }
}
