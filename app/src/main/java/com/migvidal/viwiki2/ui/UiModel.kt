package com.migvidal.viwiki2.ui

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay


data class UiDayData(
    val databaseFeaturedArticle: DatabaseFeaturedArticle?,
    val databaseMostReadArticles: List<DatabaseArticle>?,
    val image: UiDayImage?,
    val databaseOnThisDay: List<DatabaseOnThisDay>?,
)

data class UiDayImage(
    val id: Long,
    val thumbnail: DatabaseImage,
    val fullSizeImage: DatabaseImage,
    val description: String,
    val title: String,
    val fileUrl: String,
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
            fileUrl = databaseDayImage.filePage,
            thumbnail = thumbnail,
            fullSizeImage = fullSizeImage,
            description = description.text,
        )
    }
}






