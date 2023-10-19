package com.migvidal.viwiki2.data.database

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.network.NetworkArticle
import com.migvidal.viwiki2.data.network.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.NetworkImage

fun NetworkFeaturedArticle.toDatabaseModel(originalImageId: Long, thumbnailId: Long) =
    DatabaseFeaturedArticle(
        originalImageId = originalImageId,
        thumbnailId = thumbnailId,
        type = type,
        title = title,
        displayTitle = displayTitle,
        description = description,
        extract = extract,
        normalizedTitle = normalizedTitle
    )


fun NetworkImage.toDatabaseModel() = DatabaseImage(
    source, width, height
)

fun NetworkArticle.toDatabaseModel(thumbnailId: Long, isOnThisDay: Boolean, isMostRead: Boolean) = DatabaseArticle(
    views = views,
    normalizedTitle = normalizedTitle,
    description = description ?: "",
    extract = extract,
    thumbnailId = thumbnailId,
    isOnThisDay = isOnThisDay,
    isMostRead = isMostRead,
)