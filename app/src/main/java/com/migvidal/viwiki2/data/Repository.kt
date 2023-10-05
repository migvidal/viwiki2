package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.data.database.DayData
import com.migvidal.viwiki2.data.database.FeaturedArticle
import com.migvidal.viwiki2.data.database.Image
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import kotlinx.coroutines.flow.combine

class Repository(private val viWikiDatabase: ViWikiDatabaseSpec) {
    /**
     * Single source of truth for the "today" response
     */
    val dayData = combine(
        flow = viWikiDatabase.featuredArticleDao.getAll(),
        flow2 = viWikiDatabase.mostReadDao.getMostReadAndArticles(),
        flow3 = viWikiDatabase.dayImageDao.getAll(),
        flow4 = viWikiDatabase.onThisDayDao.getAll(),
    ) { featuredArticle, mostRead, image, onThisDay ->
        DayData(
            featuredArticle = featuredArticle,
            mostRead = mostRead.keys.first(),
            image = image,
            onThisDay = onThisDay,
        )
    }

    fun refreshDayData() {
        // to the repo
        val fakeArticle = FeaturedArticle(
            type = "standard",
            title = "Foo_of_the_bar",
            displayTitle = "<span class=\"mw-page-title-main\">Foo of the bar</span>",
            normalizedTitle = "Foo of the bar",
            originalImage = Image(source = "foo.bar/image.png", width = 400, height = 400),
            thumbnail = Image(source = "foo.bar/full-image.png", width = 4000, height = 4000),
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.",
            extract = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        )
        viWikiDatabase.featuredArticleDao.insert(featuredArticle = fakeArticle)
    }
}