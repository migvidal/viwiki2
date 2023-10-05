package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.data.database.DayResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Repository() {
    /**
     * Single source of truth for the "today" response
     */
    private val _dayData = MutableSharedFlow<DayResponse>()
    val dayData: SharedFlow<DayResponse>
        get() = _dayData.asSharedFlow()

    fun refreshDayData() {

    }
}