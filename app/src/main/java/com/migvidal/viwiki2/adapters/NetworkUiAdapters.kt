package com.migvidal.viwiki2.adapters

import com.migvidal.viwiki2.data.network.NetworkImage
import com.migvidal.viwiki2.data.network.article.NetworkQuery
import com.migvidal.viwiki2.ui.UiArticle

fun NetworkQuery.NetworkArticle.toUiModel(
    thumbnail: NetworkImage,
    fullSizeImage: NetworkImage
): UiArticle {
    return UiArticle(
        id = this.pageId,
        thumbnail = thumbnail.toDatabaseModel(),
        fullSizeImage = fullSizeImage.toDatabaseModel(),
        normalizedTitle = this.title,
        description = null,
        extract = this.extract,
        isSaved = false,
    )
}