package com.migvidal.viwiki2.data.repository

import com.migvidal.viwiki2.data.network.article.ArticleResponseModel
import com.migvidal.viwiki2.data.network.search.WikipediaApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Single source of truth for the "article" response
 */
class ArticleRepository : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    private val _data = MutableStateFlow(ArticleResponseModel())
    override val data = _data.asStateFlow()

    suspend fun refreshArticleData(pageId: Int) {
        _dataStatus.update { Repository.Status.Loading }
        _data.update {
            try {
                val articleResponse = WikipediaApiImpl.wikipediaApiService.getArticleById(pageId = pageId)
                _dataStatus.update { Repository.Status.Success }
                articleResponse
            } catch (e: Exception) {
                _dataStatus.update { Repository.Status.Error }
                ArticleResponseModel()
            }
        }
    }
}