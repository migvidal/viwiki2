package com.migvidal.viwiki2.ui.screens.saved_articles_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.migvidal.viwiki2.data.database.ViWikiDatabase
import com.migvidal.viwiki2.data.network.day.NetworkArticle
import com.migvidal.viwiki2.data.repository.SavedArticlesRepository
import kotlinx.coroutines.launch

class SavedArticlesViewModel(private val repository: SavedArticlesRepository) : ViewModel() {
    val savedArticles = repository.data

    fun saveArticle(networkArticle: NetworkArticle) {
        viewModelScope.launch {
            repository.saveArticle(networkArticle)
        }
    }
    fun unsaveArticle(networkArticle: NetworkArticle) {
        viewModelScope.launch {
            repository.unsaveArticle(networkArticle)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val applicationContext =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]?.applicationContext
                val database = ViWikiDatabase.getInstance(applicationContext = applicationContext!!)
                SavedArticlesViewModel(repository = SavedArticlesRepository(database))
            }
        }
    }
}