package com.thriftlane.data

interface ItemRepository {
    suspend fun getItems(): List<Item>
    suspend fun getItem(id: Int): Item?
}
