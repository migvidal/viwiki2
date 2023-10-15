package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.ui.UiDayData

val fakeFeaturedArticle = DatabaseFeaturedArticle(
    originalImageId = 10000,
    thumbnailId = 20000,
    type = "standard",
    title = "Foo_of_the_bar",
    displayTitle = "<span class=\"mw-page-title-main\">Foo of the bar</span>",
    normalizedTitle = "Foo of the bar",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.",
    extract = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
)
val fakeArticles = listOf(
    DatabaseArticle(
        normalizedTitle = "Short", description = "Ut enim ad.", extract = "Ut enim ad minim veniam."
    )
) + List(10) {
    DatabaseArticle(
        normalizedTitle = "Foo of the bar",
        description = "Ut enim ad.",
        extract = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
    )
}

val fakeDayData = UiDayData(
    databaseFeaturedArticle = fakeFeaturedArticle,
    databaseMostReadArticles = fakeArticles,
    image = null,
    databaseOnThisDay = null,
)