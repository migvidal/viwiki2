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
        _data.update {
            WikipediaApiImpl.wikipediaApiService.getArticleById(pageId = pageId)
        }
    }
}