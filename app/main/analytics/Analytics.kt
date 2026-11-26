package com.thriftlane.analytics

import android.util.Log

interface Analytics {
    fun track(event: String, properties: Map<String, String> = emptyMap())
}

/** Minimal implementation that writes events to Logcat. */
class LogAnalytics : Analytics {
    override fun track(event: String, properties: Map<String, String>) {
        Log.d("Analytics", "$event $properties")
    }
}
