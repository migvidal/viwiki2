package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.data.database.DayData
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.database.entities.Article
import com.migvidal.viwiki2.data.database.entities.FeaturedArticle
import com.migvidal.viwiki2.data.database.entities.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

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
            featuredArticle = featuredArticle,
            mostRead = mostRead,
            image = image,
            onThisDay = onThisDay,
        )
    }

    suspend fun refreshDayData() {
        // to the repo
        val fakeArticle = Article("Lorem Ipsum")
        val fakeImage = Image()
        val fakeThumbnail = Image()

        withContext(context = Dispatchers.IO) {
            val articleId = viWikiDatabase.articleDao.insert(article = fakeArticle)
            val imageId = viWikiDatabase.imageDao.insert(image = fakeImage)
            val thumbnailId = viWikiDatabase.imageDao.insert(image = fakeThumbnail)

            val fakeFeaturedArticle = FeaturedArticle(
                articleId = articleId,
                originalImageId = imageId,
                thumbnailId = thumbnailId,
                type = "standard",
                title = "Foo_of_the_bar",
                displayTitle = "<span class=\"mw-page-title-main\">Foo of the bar</span>",
                normalizedTitle = "Foo of the bar",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.",
                extract = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            )

            viWikiDatabase.featuredArticleDao.insert(featuredArticle = fakeFeaturedArticle)
        }
    }
}