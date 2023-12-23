package com.migvidal.viwiki2.data.repository

import android.util.Log
import com.migvidal.viwiki2.adapters.toDatabaseModel
import com.migvidal.viwiki2.adapters.toUiArticle
import com.migvidal.viwiki2.adapters.toUiDayArticle
import com.migvidal.viwiki2.adapters.toUiDayImage
import com.migvidal.viwiki2.adapters.toUiFeaturedArticle
import com.migvidal.viwiki2.adapters.toUiOnThisDay
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.network.day.NetworkDayImage
import com.migvidal.viwiki2.data.network.day.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.day.NetworkMostRead
import com.migvidal.viwiki2.data.network.day.NetworkOnThisDay
import com.migvidal.viwiki2.data.network.day.WikiMediaApiImpl
import com.migvidal.viwiki2.ui.UiDayData
import com.migvidal.viwiki2.ui.UiDayImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.util.GregorianCalendar

/**
 * Single source of truth for the "today" response
 */
class DayRepository(private val viWikiDatabase: ViWikiDatabaseSpec) : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    override val data: Flow<UiDayData> = combine(
        flow = viWikiDatabase.featuredArticleDao.getAll(),
        flow2 = viWikiDatabase.mostReadArticlesDao.getMostRead(),
        flow3 = viWikiDatabase.dayImageDao.getAll(),
        flow4 = viWikiDatabase.onThisDayDao.getAllOnThisDay(),
    ) { featuredArticle, mostRead, image, onThisDay ->
        // Image of the day
        val uiDayImage: UiDayImage? = run {
            image ?: return@run null
            val thumbnail = viWikiDatabase.imageDao.getImageById(image.thumbnailId)
            val fullSize = viWikiDatabase.imageDao.getImageById(image.imageId)
            image.toUiDayImage(
                thumbnail = thumbnail ?: return@run null,
                fullSizeImage = fullSize ?: return@run null,
            )
        }
        // Featured article
        val uiFeaturedArticle = featuredArticle?.let { databaseArticle ->
            val thumbnail = databaseArticle.thumbnailId?.let {
                viWikiDatabase.imageDao.getImageById(it)
            }
            val fullSize = databaseArticle.originalImageId?.let {
                viWikiDatabase.imageDao.getImageById(it)
            }
            featuredArticle.toUiFeaturedArticle(
                thumbnail = thumbnail,
                fullSizeImage = fullSize,
            )
        }
        // On this day
        val uiOnThisDay = onThisDay?.map { databaseOnThisDay ->
            val yearArticles =
                viWikiDatabase.onThisDayDao.getArticlesForYear(databaseOnThisDay.year)
            val uiYearArticles = yearArticles?.map { databaseArticle ->
                databaseArticle.toUiArticle(
                    thumbnail = databaseArticle.thumbnailId?.let {
                        viWikiDatabase.imageDao.getImageById(it)
                    },
                    fullSizeImage = databaseArticle.originalImageId?.let {
                        viWikiDatabase.imageDao.getImageById(it)
                    },
                )
            }

            databaseOnThisDay.toUiOnThisDay(yearArticles = uiYearArticles)
        }

        // Finally, create the UiDay object
        UiDayData(
            featuredArticle = uiFeaturedArticle,
            mostReadArticles = mostRead.map { databaseArticle ->
                val thumbnail = databaseArticle.thumbnailId?.let {
                    viWikiDatabase.imageDao.getImageById(id = it)
                }
                databaseArticle.toUiDayArticle(thumbnail = thumbnail)
            },
            image = uiDayImage,
            onThisDay = uiOnThisDay,
        )
    }

    suspend fun refreshData() {
        _dataStatus.update { Repository.Status.Loading }
        // Get today's date
        val calendar = GregorianCalendar.getInstance()
        val year = calendar.get(GregorianCalendar.YEAR)
        val month = calendar.get(GregorianCalendar.MONTH)
        val day = calendar.get(GregorianCalendar.DAY_OF_MONTH)

        // Get today data from the network
        val networkDayResponse = runCatching {
            WikiMediaApiImpl.wikiMediaApiService.getFeatured(
                yyyy = year.toString(),
                mm = String.format("%02d", month),
                dd = String.format("%02d", day),
            )
        }.getOrElse {
            if (it is UnknownHostException) {
                Log.e("OkHttp error", it.message.toString())
            }
            Log.e("Network error", it.message.toString())
            it.printStackTrace()
            _dataStatus.update { Repository.Status.Error }
            return
        }

        // Cache data in DB
        withContext(context = Dispatchers.IO) {
            networkDayResponse.featuredArticle?.let {
                cacheFeaturedArticle(it)
            }
            networkDayResponse.image?.let {
                cacheDayImage(it)
            }
            networkDayResponse.mostRead?.let {
                cacheMostRead(it)
            }
            networkDayResponse.onThisDay?.let {
                cacheOnThisDay(it)
            }
        }
        _dataStatus.update { Repository.Status.Success }
    }

    private suspend fun cacheFeaturedArticle(featuredArticle: NetworkFeaturedArticle) {
        // Insert fields
        featuredArticle.originalImage?.let {
            viWikiDatabase.imageDao.insert(databaseImage = it.toDatabaseModel())
        }
        featuredArticle.thumbnail?.let {
            viWikiDatabase.imageDao.insert(databaseImage = it.toDatabaseModel())
        }
        // Insert
        val dbFeaturedArticle = featuredArticle.toDatabaseModel()
        viWikiDatabase.featuredArticleDao.insert(featuredArticle = dbFeaturedArticle)
    }

    private suspend fun cacheMostRead(mostRead: NetworkMostRead) {
        // Convert to DB model
        val databaseMostRead = mostRead.articles.map { networkArticle ->
            val thumbnail = networkArticle.thumbnail?.let {
                DatabaseImage(
                    sourceAndId = it.source, width = it.width, height = it.height
                )
            }
            thumbnail ?: return@map null
            viWikiDatabase.imageDao.insert(thumbnail)

            val originalImage = networkArticle.originalImage?.let {
                // - insert article image
                DatabaseImage(
                    sourceAndId = it.source, width = it.width, height = it.height
                )
            }
            originalImage ?: return@map null
            viWikiDatabase.imageDao.insert(originalImage)

            return@map networkArticle.toDatabaseModel(
                isMostRead = true,
            )
        }
        // Insert
        viWikiDatabase.mostReadArticlesDao.insertAll(
            *databaseMostRead.filterNotNull().toTypedArray()
        )
    }

    private suspend fun cacheDayImage(dayImage: NetworkDayImage) {
        dayImage.let {
            // Insert referenced fields
            val dbThumbnail = DatabaseImage(
                sourceAndId = it.thumbnail.source,
                width = it.thumbnail.width,
                height = it.thumbnail.height,
            )
            viWikiDatabase.imageDao.insert(dbThumbnail)
            val dbFullSizeImage = DatabaseImage(
                sourceAndId = it.image.source,
                width = it.image.width,
                height = it.image.height,
            )
            viWikiDatabase.imageDao.insert(dbFullSizeImage)
            // Insert
            val dbDayImage = DatabaseDayImage(
                thumbnailId = dbThumbnail.sourceAndId,
                imageId = dbFullSizeImage.sourceAndId,
                description = it.description.text,
                titleAndId = it.title,
            )
            viWikiDatabase.dayImageDao.insert(dbDayImage)
        }
    }

    private suspend fun cacheOnThisDay(onThisDayList: List<NetworkOnThisDay>) {
        // Insert articles' images
        onThisDayList.forEach { networkOnThisDay ->
            networkOnThisDay.pages.forEach { networkArticle ->
                val dbThumbnail = networkArticle.thumbnail?.toDatabaseModel()
                val dbImage = networkArticle.originalImage?.toDatabaseModel()
                dbThumbnail?.let { viWikiDatabase.imageDao.insert(it) }
                dbImage?.let { viWikiDatabase.imageDao.insert(it) }
            }
        }
        // Insert articles
        val articles = onThisDayList.flatMap { networkOnThisDay ->
            networkOnThisDay.pages.map { article ->
                article.toDatabaseModel(onThisDayYear = networkOnThisDay.year)
            }
        }
        val dedupedArticles: List<DatabaseArticle> = articles.distinctBy { it.articleId }
        viWikiDatabase.articleDao.insertAll(databaseArticle = dedupedArticles.toTypedArray())
        // Insert
        val databaseOnThisDayList = onThisDayList.map {
            it.toDatabaseModel()
        }
        viWikiDatabase.onThisDayDao.insertAll(
            databaseOnThisDay = databaseOnThisDayList.toTypedArray()
        )
    }
}