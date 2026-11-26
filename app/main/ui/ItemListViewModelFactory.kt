package com.thriftlane.ui.list

import android.app.Application
import android.provider.Settings
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thriftlane.analytics.LogAnalytics
import com.thriftlane.data.FakeItemRepository
import com.thriftlane.experiment.ExperimentService

val itemListViewModelFactory = viewModelFactory {
    initializer {
        val app = this[APPLICATION_KEY] as Application
        val userId = Settings.Secure.getString(app.contentResolver, Settings.Secure.ANDROID_ID) ?: "anonymous"
        ItemListViewModel(
            repository = FakeItemRepository(),
            experiments = ExperimentService(userId),
            analytics = LogAnalytics(),
        )
    }
}
