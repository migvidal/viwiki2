package com.migvidal.viwiki2.adapters

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.network.NetworkImage
import com.migvidal.viwiki2.data.network.day.NetworkArticle
import com.migvidal.viwiki2.data.network.day.NetworkFeaturedArticle

fun NetworkFeaturedArticle.toDatabaseModel() =
    DatabaseArticle(
        articleId = this.pageId,
        originalImageId = this.originalImage.source,
        thumbnailId = this.originalImage.source,
        description = this.description,
        extract = this.extract,
        normalizedTitle = this.normalizedTitle,
        views = null,
    )


fun NetworkImage.toDatabaseModel() = DatabaseImage(
    sourceAndId = this.source,
    width = this.width,
    height = this.height
)

fun NetworkArticle.toDatabaseModel(
    isOnThisDay: Boolean,
    isMostRead: Boolean,
    isFeatured: Boolean,
): DatabaseArticle? {
    this.thumbnail ?: return null
    return DatabaseArticle(
        articleId = this.pageId,
        views = this.views,
        normalizedTitle = this.normalizedTitle,
        description = this.description ?: "",
        extract = this.extract,
        thumbnailId = this.thumbnail.source,
        originalImageId = this.thumbnail.source,
        isOnThisDay = isOnThisDay,
        isMostRead = isMostRead,
        isFeatured = isFeatured,
    )
}