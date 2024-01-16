package com.migvidal.viwiki2.data.repository

import com.migvidal.viwiki2.adapters.toDatabaseModel
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.network.day.NetworkArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Single source of truth for the "article" response
 */
class SavedArticlesRepository(private val viWikiDatabase: ViWikiDatabaseSpec) : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    override val data = viWikiDatabase.articleDao.getSavedArticles()

    suspend fun saveArticle(networkArticle: NetworkArticle) {
        val dbArticle = networkArticle.toDatabaseModel(isSaved = true)
        viWikiDatabase.articleDao.insertAll(dbArticle)
    }

    suspend fun unsaveArticle(networkArticle: NetworkArticle) {
        val dbArticle = networkArticle.toDatabaseModel(isSaved = true)
        viWikiDatabase.articleDao.delete(dbArticle)
    }
}