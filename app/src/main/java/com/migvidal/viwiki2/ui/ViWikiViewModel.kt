package com.migvidal.viwiki2.ui

import androidx.lifecycle.ViewModel
import com.migvidal.viwiki2.data.database.DayData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ViWikiViewModel: ViewModel() {
    private val _dayData = MutableSharedFlow<DayData>()
    val dayData: SharedFlow<DayData>
        get() = _dayData.asSharedFlow()
}