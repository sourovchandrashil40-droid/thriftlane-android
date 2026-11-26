package com.thriftlane.ui.list

import com.thriftlane.analytics.Analytics
import com.thriftlane.data.Item
import com.thriftlane.data.ItemRepository
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) = Dispatchers.setMain(dispatcher)
    override fun finished(description: Description) = Dispatchers.resetMain()
}

class StubRepository(private val items: List<Item>) : ItemRepository {
    override suspend fun getItems() = items
    override suspend fun getItem(id: Int) = items.firstOrNull { it.id == id }
}

/** Fails on the first [failures] calls, then succeeds. */
class FlakyRepository(private val items: List<Item>, private var failures: Int = 1) : ItemRepository {
    override suspend fun getItems(): List<Item> {
        if (failures > 0) {
            failures--
            throw IOException("network down")
        }
        return items
    }

    override suspend fun getItem(id: Int) = items.firstOrNull { it.id == id }
}

class FakeAnalytics : Analytics {
    val events = mutableListOf<Pair<String, Map<String, String>>>()
    override fun track(event: String, properties: Map<String, String>) {
        events += event to properties
    }
}
