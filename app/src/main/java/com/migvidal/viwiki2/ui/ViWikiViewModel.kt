package com.migvidal.viwiki2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.migvidal.viwiki2.data.Repository
import com.migvidal.viwiki2.data.database.ViWikiDatabase
import kotlinx.coroutines.launch

class ViWikiViewModel(private val repository: Repository) : ViewModel() {
    init {
        refreshDataFromRepository()
    }

    val dayData = repository.dayData
    val dayDataStatus = repository.dayDataStatus

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            repository.refreshDayData()
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val applicationContext = this[APPLICATION_KEY]?.applicationContext
                val database = ViWikiDatabase.getInstance(applicationContext = applicationContext!!)
                ViWikiViewModel(repository = Repository(database))
            }
        }
    }
}