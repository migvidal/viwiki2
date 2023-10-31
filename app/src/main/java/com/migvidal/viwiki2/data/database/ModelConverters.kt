package com.migvidal.viwiki2.data.database

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.network.NetworkArticle
import com.migvidal.viwiki2.data.network.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.NetworkImage

fun NetworkFeaturedArticle.toDatabaseModel(originalImageId: Long, thumbnailId: Long) =
    DatabaseArticle(
        originalImageId = originalImageId,
        thumbnailId = thumbnailId,
        description = description,
        extract = extract,
        normalizedTitle = normalizedTitle,
        views = null,
    )


fun NetworkImage.toDatabaseModel() = DatabaseImage(
    source, width, height
)

fun NetworkArticle.toDatabaseModel(
    thumbnailId: Long?,
    originalImageId: Long?,
    isOnThisDay: Boolean,
    isMostRead: Boolean,
    isFeatured: Boolean,
) = DatabaseArticle(
    views = views,
    normalizedTitle = normalizedTitle,
    description = description ?: "",
    extract = extract,
    thumbnailId = thumbnailId,
    originalImageId = originalImageId,
    isOnThisDay = isOnThisDay,
    isMostRead = isMostRead,
    isFeatured = isFeatured,
)