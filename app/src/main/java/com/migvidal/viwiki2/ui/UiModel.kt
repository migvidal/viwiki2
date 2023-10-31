package com.migvidal.viwiki2.ui

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay


data class UiDayData(
    val featuredArticle: UiFeaturedArticle?,
    val mostReadArticles: List<UiArticle>?,
    val image: UiDayImage?,
    val databaseOnThisDay: List<DatabaseOnThisDay>?,
)

data class UiArticle(
    val views: Int?,
    val normalizedTitle: String = "",
    val description: String,
    val extract: String,
    val thumbnail: DatabaseImage?,
) {
    companion object {
        fun fromDatabaseEntity(
            databaseArticle: DatabaseArticle,
            thumbnail: DatabaseImage?,
        ) = UiArticle(
            views = databaseArticle.views,
            normalizedTitle = databaseArticle.normalizedTitle,
            description = databaseArticle.description,
            extract = databaseArticle.extract,
            thumbnail = thumbnail
        )
    }
}

data class UiDayImage(
    val id: Long,
    val thumbnail: DatabaseImage,
    val fullSizeImage: DatabaseImage,
    val description: String,
    val title: String,
) {
    companion object {
        fun fromDatabaseEntity(
            databaseDayImage: DatabaseDayImage,
            thumbnail: DatabaseImage,
            fullSizeImage: DatabaseImage,
            description: DatabaseDescription
        ) = UiDayImage(
            id = databaseDayImage.dayImageId,
            title = databaseDayImage.title,
            thumbnail = thumbnail,
            fullSizeImage = fullSizeImage,
            description = description.text,
        )
    }
}

data class UiFeaturedArticle(
    val thumbnail: DatabaseImage,
    val fullSizeImage: DatabaseImage,
    val normalizedTitle: String,
    val description: String,
    val extract: String,
) {
    companion object {
        fun fromDatabaseEntity(
            featuredArticle: DatabaseArticle,
            thumbnail: DatabaseImage,
            fullSizeImage: DatabaseImage,
        ) = UiFeaturedArticle(
            thumbnail = thumbnail,
            fullSizeImage = fullSizeImage,
            normalizedTitle = featuredArticle.normalizedTitle,
            description = featuredArticle.description,
            extract = featuredArticle.extract,
        )
    }
}







