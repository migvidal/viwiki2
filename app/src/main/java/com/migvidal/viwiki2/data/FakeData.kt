package com.migvidal.viwiki2.data

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.ui.UiDayData
import com.migvidal.viwiki2.ui.UiDayImage

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

const val DummyImageUrl =
    "https://upload.wikimedia.org/wikipedia/commons/c/c6/Golden-eyed_tree_frog_%28Agalychnis_annae%29.jpg"
val fakeDatabaseImage = DatabaseImage(source = DummyImageUrl, width = 300, height = 300)
val fakeUiDayImage = UiDayImage(
    id = 1000,
    thumbnail = fakeDatabaseImage,
    fullSizeImage = fakeDatabaseImage,
    description = "Goes boing and ribbit",
    title = "A frog"
)