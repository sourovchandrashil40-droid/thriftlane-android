package com.thriftlane.data

import java.util.Locale

data class Item(
    val id: Int,
    val title: String,
    val brand: String,
    val size: String,
    val priceEur: Double,
    val condition: String,
    val description: String,
) {
    val formattedPrice: String
        get() = String.format(Locale.US, "€%.2f", priceEur)
}
