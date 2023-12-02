package com.migvidal.viwiki2.ui.screens.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.migvidal.viwiki2.data.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    val searchData = repository.data
    val searchDataStatus = repository.dataStatus

    fun refreshSearchDataFromRepository(query: String) {
        viewModelScope.launch {
            repository.refreshSearchData(query = query)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val applicationContext = this[APPLICATION_KEY]?.applicationContext
                SearchViewModel(repository = SearchRepository())
            }
        }
    }
}