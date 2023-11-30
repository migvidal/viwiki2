package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.ui.UiDayData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    val dayDataStatus: StateFlow<Status>
    /**
     * Single source of truth for the "today" response
     */
    val data: Flow<UiDayData>
    suspend fun refreshData()
    enum class Status { Error, Success, Loading }
}