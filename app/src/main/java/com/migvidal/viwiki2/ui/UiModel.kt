package com.migvidal.viwiki2.ui

import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay


data class UiDayData(
    val featuredArticle: UiFeaturedArticle?,
    val mostReadArticles: List<UiDayArticle>?,
    val image: UiDayImage?,
    val databaseOnThisDay: List<DatabaseOnThisDay>?,
)

data class UiDayArticle(
    val id: Int,
    val views: Int?,
    val normalizedTitle: String = "",
    val description: String,
    val extract: String,
    val thumbnail: DatabaseImage?,
)

data class UiDayImage(
    val id: Long,
    val thumbnail: DatabaseImage,
    val fullSizeImage: DatabaseImage,
    val description: String,
    val title: String,
)

data class UiFeaturedArticle(
    val id: Int,
    val thumbnail: DatabaseImage,
    val fullSizeImage: DatabaseImage,
    val normalizedTitle: String,
    val description: String,
    val extract: String,
)







