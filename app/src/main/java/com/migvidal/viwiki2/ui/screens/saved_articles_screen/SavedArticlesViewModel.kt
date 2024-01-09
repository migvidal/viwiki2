package com.migvidal.viwiki2.ui.screens.saved_articles_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.migvidal.viwiki2.data.database.ViWikiDatabase
import com.migvidal.viwiki2.data.repository.SavedArticlesRepository

class SavedArticlesViewModel(private val repository: SavedArticlesRepository) : ViewModel() {
    val savedArticles = repository.data

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val applicationContext = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]?.applicationContext
                val database = ViWikiDatabase.getInstance(applicationContext = applicationContext!!)
                SavedArticlesViewModel(repository = SavedArticlesRepository(database))
            }
        }
    }
}