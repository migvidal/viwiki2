package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.data.database.DayData
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.database.toDatabaseImage
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
        flow2 = viWikiDatabase.mostReadDao.getMostRead(),
        flow3 = viWikiDatabase.dayImageDao.getAll(),
        flow4 = viWikiDatabase.onThisDayDao.getAll(),
    ) { featuredArticle, mostRead, image, onThisDay ->
        DayData(
            databaseFeaturedArticle = featuredArticle,
            databaseMostRead = mostRead,
            image = image,
            databaseOnThisDay = onThisDay,
        )
    }

    suspend fun refreshDayData() {
        val calendar = GregorianCalendar.getInstance()
        val year = calendar.get(GregorianCalendar.YEAR)
        val month = calendar.get(GregorianCalendar.MONTH)
        val day = calendar.get(GregorianCalendar.DAY_OF_MONTH)

        // from the network
        val todayResponse = WikiMediaApiImpl.wikiMediaApiService.getFeatured(
            yyyy = year.toString(),
            mm = String.format("%02d", month),
            dd = String.format("%02d", day),
        )
        // to the repo
        val article = todayResponse.featuredArticle?: return
        val image = todayResponse.featuredArticle.originalImage
        val thumbnail = todayResponse.featuredArticle.thumbnail

        val articles = todayResponse.mostRead?.articles

        withContext(context = Dispatchers.IO) {
            val insertedImageId =
                viWikiDatabase.imageDao.insert(databaseImage = image.toDatabaseImage())
            val insertedThumbnailId =
                viWikiDatabase.imageDao.insert(databaseImage = thumbnail.toDatabaseImage())
            /*val fakeFeaturedArticle = FeaturedArticle(
                articleId = articleId,
                originalImageId = imageId,
                thumbnailId = thumbnailId,
                type = "standard",
                title = "Foo_of_the_bar",
                displayTitle = "<span class=\"mw-page-title-main\">Foo of the bar</span>",
                normalizedTitle = "Foo of the bar",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.",
                extract = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            )*/

            val articlesToInsert = articles?.map {
                it.toDatabaseModel()
            } ?: return@withContext

            viWikiDatabase.articleDao.insertAll(
                *articlesToInsert.toTypedArray()
            )
        }
    }
}