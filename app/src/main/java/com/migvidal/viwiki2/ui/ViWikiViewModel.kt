package com.migvidal.viwiki2.ui

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.migvidal.viwiki2.ViWikiApplication
import com.migvidal.viwiki2.data.Repository
import com.migvidal.viwiki2.data.database.ViWikiDatabase
import kotlinx.coroutines.launch

class ViWikiViewModel(viWikiApplication: ViWikiApplication) : AndroidViewModel(viWikiApplication) {
    init {
        refreshDataFromRepository()
    }

    private val repository = Repository(ViWikiDatabase.getInstance(viWikiApplication))

    val dayData = repository.dayData

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            repository.refreshDayData()
        }
    }
}