package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.data.database.DayData
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseMostReadArticle
import com.migvidal.viwiki2.data.database.toDatabaseModel
import com.migvidal.viwiki2.data.network.WikiMediaApiImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import java.util.GregorianCalendar

class Repository(private val viWikiDatabase: ViWikiDatabaseSpec) {
    /**
     * Single source of truth for the "today" response
     */
    val dayData = combine(
        flow = viWikiDatabase.featuredArticleDao.getAll(),
        flow2 = viWikiDatabase.mostReadArticlesDao.getMostReadAndArticles(),
        flow3 = viWikiDatabase.dayImageDao.getAll(),
        flow4 = viWikiDatabase.onThisDayDao.getAll(),
    ) { featuredArticle, mostRead, image, onThisDay ->
        DayData(
            databaseFeaturedArticle = featuredArticle,
            databaseMostReadArticles = mostRead.values.flatten(),
            image = image,
            databaseOnThisDay = onThisDay,
        )
    }

    suspend fun refreshDayData() {
        val calendar = GregorianCalendar.getInstance()
        val year = calendar.get(GregorianCalendar.YEAR)
        val month = calendar.get(GregorianCalendar.MONTH)
        val day = calendar.get(GregorianCalendar.DAY_OF_MONTH)

        // Get from the network
        val networkDayResponse = WikiMediaApiImpl.wikiMediaApiService.getFeatured(
            yyyy = year.toString(),
            mm = String.format("%02d", month),
            dd = String.format("%02d", day),
        )
        val featuredArticle = networkDayResponse.featuredArticle ?: return
        val image = featuredArticle.originalImage
        val thumbnail = featuredArticle.thumbnail
        val mostRead = networkDayResponse.mostRead

        // Insert in DB
        withContext(context = Dispatchers.IO) {
            // Insert "featured article"
            // - insert full img and thumbnail
            val insertedImageId =
                viWikiDatabase.imageDao.insert(databaseImage = image.toDatabaseModel())
            val insertedThumbnailId =
                viWikiDatabase.imageDao.insert(databaseImage = thumbnail.toDatabaseModel())

            // - insert the featured article
            val dbFeaturedArticle = featuredArticle.toDatabaseModel(
                originalImageId = insertedImageId,
                thumbnailId = insertedThumbnailId
            )
            viWikiDatabase.featuredArticleDao.insert(featuredArticle = dbFeaturedArticle)

            // Insert "most read articles"
            if (mostRead != null) {
                // - insert articles
                val dbMostReadArticles: List<DatabaseArticle> = mostRead.articles.map {
                    it.toDatabaseModel()
                }
                val insertedArticlesIDs = viWikiDatabase.articleDao.insertAll(*dbMostReadArticles.toTypedArray())

                // - insert mostReadArticles entity
                val mostReadArticles = insertedArticlesIDs.map {
                    DatabaseMostReadArticle(articleId = it, date = mostRead.date)
                }
                viWikiDatabase.mostReadArticlesDao.insertAll(
                    *mostReadArticles.toTypedArray()
                )
            }
        }
    }
}