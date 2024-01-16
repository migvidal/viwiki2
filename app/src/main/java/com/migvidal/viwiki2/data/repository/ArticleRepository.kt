package com.migvidal.viwiki2.data.repository

import com.migvidal.viwiki2.adapters.toUiArticle
import com.migvidal.viwiki2.adapters.toUiModel
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.network.NetworkImage
import com.migvidal.viwiki2.data.network.search.WikipediaApiImpl
import com.migvidal.viwiki2.ui.UiArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Single source of truth for the "article" response
 */
class ArticleRepository(private val viWikiDatabase: ViWikiDatabaseSpec) : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    private val _data: MutableStateFlow<UiArticle?> = MutableStateFlow(null)
    override val data = _data.asStateFlow()

    suspend fun refreshArticleData(pageId: Int) {
        _dataStatus.update { Repository.Status.Loading }
        _data.update {
            try {
                // Check database
                val articleFromDb = viWikiDatabase.articleDao.getArticleById(id = pageId)
                if (articleFromDb != null) {
                    val thumbnail = articleFromDb.thumbnailId?.let { thumbnailId ->
                        viWikiDatabase.imageDao.getImageById(thumbnailId)
                    }
                    val originalImage =
                        articleFromDb.originalImageId?.let { imageId ->
                            viWikiDatabase.imageDao.getImageById(imageId)
                        }
                    return@update articleFromDb.toUiArticle(
                        thumbnail = thumbnail,
                        fullSizeImage = originalImage
                    )
                }

                // Check the network
                val articleResponse =
                    WikipediaApiImpl.wikipediaApiService.getArticleById(pageId = pageId)
                _dataStatus.update { Repository.Status.Success }
                // Convert network article to UiArticle
                val networkArticle = articleResponse.query.pages.first()
                val image = networkArticle.original?.let {
                    NetworkImage(source = it.source, width = it.width, height = it.height)
                }
                return@update image?.let {
                    networkArticle.toUiModel(
                        thumbnail = it, fullSizeImage = it
                    )
                }
            } catch (e: Exception) {
                _dataStatus.update { Repository.Status.Error }
                null
            }
        }
    }
}