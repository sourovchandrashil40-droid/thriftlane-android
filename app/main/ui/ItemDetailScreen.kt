package com.thriftlane.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.thriftlane.data.Item

@Composable
fun ItemDetailScreen(
    item: Item?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp).testTag("detail_screen")) {
        TextButton(onClick = onBack) { Text("← Back") }
        if (item == null) {
            Text("Item not found")
            return@Column
        }
        Text(item.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(4.dp))
        Text(item.formattedPrice, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        Text("Brand: ${item.brand}")
        Text("Size: ${item.size}")
        Text("Condition: ${item.condition}")
        Spacer(Modifier.height(12.dp))
        Text(item.description, style = MaterialTheme.typography.bodyMedium)
    }
}
