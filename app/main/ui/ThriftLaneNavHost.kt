package com.thriftlane.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thriftlane.ui.detail.ItemDetailScreen
import com.thriftlane.ui.list.ItemListScreen
import com.thriftlane.ui.list.ItemListViewModel

@Composable
fun ThriftLaneNavHost(viewModel: ItemListViewModel) {
    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ItemListScreen(
                state = state,
                onQueryChange = viewModel::onQueryChange,
                onToggleFavorite = viewModel::onToggleFavorite,
                onToggleFavoritesOnly = viewModel::onToggleFavoritesOnly,
                onItemClick = { id -> navController.navigate("detail/$id") },
                onRetry = viewModel::loadItems,
            )
        }
        composable(
            route = "detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType }),
        ) { entry ->
            val id = entry.arguments?.getInt("itemId")
            ItemDetailScreen(
                item = state.items.firstOrNull { it.id == id },
                onBack = { navController.popBackStack() },
            )
        }
    }
}
