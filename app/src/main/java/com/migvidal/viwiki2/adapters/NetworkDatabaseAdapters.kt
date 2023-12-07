package com.migvidal.viwiki2.adapters

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.network.NetworkImage
import com.migvidal.viwiki2.data.network.day.NetworkArticle
import com.migvidal.viwiki2.data.network.day.NetworkFeaturedArticle

fun NetworkFeaturedArticle.toDatabaseModel(originalImageId: Long, thumbnailId: Long) =
    DatabaseArticle(
        articleId = this.pageId,
        originalImageRowId = originalImageId,
        thumbnailRowId = thumbnailId,
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
    thumbnailId: Long,
    originalImageId: Long,
    isOnThisDay: Boolean,
    isMostRead: Boolean,
    isFeatured: Boolean,
) = DatabaseArticle(
    articleId = this.pageId,
    views = this.views,
    normalizedTitle = this.normalizedTitle,
    description = this.description ?: "",
    extract = this.extract,
    thumbnailRowId = thumbnailId,
    originalImageRowId = originalImageId,
    isOnThisDay = isOnThisDay,
    isMostRead = isMostRead,
    isFeatured = isFeatured,
)