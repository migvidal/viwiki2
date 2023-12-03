package com.migvidal.viwiki2.ui.screens.article_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.migvidal.viwiki2.data.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: ArticleRepository) : ViewModel() {

    val articleData = repository.data
    val articleDataStatus = repository.dataStatus

    fun refreshArticleDataFromRepository(pageId: Int) {
        viewModelScope.launch {
            repository.refreshArticleData(pageId = pageId)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val applicationContext = this[APPLICATION_KEY]?.applicationContext
                ArticleViewModel(repository = ArticleRepository())
            }
        }
    }
}