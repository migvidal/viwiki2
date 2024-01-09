package com.migvidal.viwiki2.adapters

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay
import com.migvidal.viwiki2.data.network.NetworkImage
import com.migvidal.viwiki2.data.network.day.NetworkArticle
import com.migvidal.viwiki2.data.network.day.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.day.NetworkOnThisDay

fun NetworkFeaturedArticle.toDatabaseModel() =
    DatabaseArticle(
        articleId = this.pageId,
        originalImageId = this.originalImage?.source,
        thumbnailId = this.originalImage?.source,
        description = this.description,
        extract = this.extract,
        normalizedTitle = this.normalizedTitle,
        views = null,
        isFeatured = true,
    )


fun NetworkImage.toDatabaseModel() = DatabaseImage(
    sourceAndId = this.source,
    width = this.width,
    height = this.height
)

fun NetworkArticle.toDatabaseModel(
    onThisDayYear: Int? = null,
    isMostRead: Boolean = false,
    isFeatured: Boolean = false,
    isSaved: Boolean = false,
): DatabaseArticle {
    return DatabaseArticle(
        articleId = this.pageId,
        views = this.views,
        normalizedTitle = this.normalizedTitle,
        description = this.description ?: "",
        extract = this.extract,
        thumbnailId = this.thumbnail?.source,
        originalImageId = this.thumbnail?.source,
        onThisDayYear = onThisDayYear,
        isMostRead = isMostRead,
        isFeatured = isFeatured,
        isSaved = isSaved,
    )
}

fun NetworkOnThisDay.toDatabaseModel(): DatabaseOnThisDay {
    return DatabaseOnThisDay(
        text = this.text,
        year = this.year,
    )
}