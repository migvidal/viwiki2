package com.migvidal.viwiki2.ui

import com.migvidal.viwiki2.data.database.entities.DatabaseImage


data class UiDayData(
    val featuredArticle: UiArticle?,
    val mostReadArticles: List<UiDayArticle>?,
    val image: UiDayImage?,
    val onThisDay: List<UiOnThisDay>?,
)

data class UiDayArticle(
    val id: Int,
    val views: Int?,
    val normalizedTitle: String?,
    val description: String?,
    val extract: String?,
    val thumbnail: DatabaseImage?,
)

data class UiDayImage(
    val idAndTitle: String,
    val thumbnail: DatabaseImage,
    val fullSizeImage: DatabaseImage,
    val description: String,
)

data class UiArticle(
    val id: Int,
    val thumbnail: DatabaseImage?,
    val fullSizeImage: DatabaseImage?,
    val normalizedTitle: String?,
    val description: String?,
    val extract: String?,
    val isSaved: Boolean,
)

data class UiOnThisDay(
    val text: String,
    val year: Int,
    val articles: List<UiArticle>?
)







