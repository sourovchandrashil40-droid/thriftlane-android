package com.thriftlane

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.thriftlane.ui.ThriftLaneNavHost
import com.thriftlane.ui.list.ItemListViewModel
import com.thriftlane.ui.list.itemListViewModelFactory
import com.thriftlane.ui.theme.ThriftLaneTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ItemListViewModel by viewModels { itemListViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThriftLaneTheme {
                ThriftLaneNavHost(viewModel)
            }
        }
    }
}
