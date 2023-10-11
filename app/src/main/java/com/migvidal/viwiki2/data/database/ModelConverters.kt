package com.migvidal.viwiki2.data.database

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseMostReadArticle
import com.migvidal.viwiki2.data.network.NetworkArticle
import com.migvidal.viwiki2.data.network.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.NetworkImage
import com.migvidal.viwiki2.data.network.NetworkMostRead

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

fun NetworkArticle.toDatabaseModel() = DatabaseArticle(
    normalizedTitle, description ?: "", extract
)

fun NetworkMostRead.toDatabaseModel(): List<DatabaseMostReadArticle> {
    val articleList = this.articles.map {
        DatabaseArticle(
            normalizedTitle = it.normalizedTitle,
            description = it.description ?: "",
            extract = it.extract
        )
    }
    return articleList.map {
        DatabaseMostReadArticle(date = this.date, articleId = it.articleId)
    }
}