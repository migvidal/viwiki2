package com.migvidal.viwiki2.adapters

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay
import com.migvidal.viwiki2.ui.UiArticle
import com.migvidal.viwiki2.ui.UiDayArticle
import com.migvidal.viwiki2.ui.UiDayImage
import com.migvidal.viwiki2.ui.UiOnThisDay

fun DatabaseArticle.toUiArticle(
    thumbnail: DatabaseImage?,
    fullSizeImage: DatabaseImage?,
) = UiArticle(
    id = articleId,
    normalizedTitle = normalizedTitle,
    description = description,
    extract = extract,
    thumbnail = thumbnail,
    fullSizeImage = fullSizeImage,
)

fun DatabaseArticle.toUiDayArticle(
    thumbnail: DatabaseImage?,
) = UiDayArticle(
    id = articleId,
    views = views,
    normalizedTitle = normalizedTitle,
    description = description,
    extract = extract,
    thumbnail = thumbnail
)

fun DatabaseDayImage.toUiDayImage(
    thumbnail: DatabaseImage,
    fullSizeImage: DatabaseImage,
) = UiDayImage(
    idAndTitle = titleAndId,
    thumbnail = thumbnail,
    fullSizeImage = fullSizeImage,
    description = description,
)

fun DatabaseArticle.toUiFeaturedArticle(
    thumbnail: DatabaseImage?,
    fullSizeImage: DatabaseImage?,
) = UiArticle(
    id = articleId,
    thumbnail = thumbnail,
    fullSizeImage = fullSizeImage,
    normalizedTitle = normalizedTitle,
    description = description,
    extract = extract,
)

fun DatabaseOnThisDay.toUiOnThisDay(yearArticles: List<UiArticle>?): UiOnThisDay {
    return UiOnThisDay(
        text = this.text,
        year = this.year,
        articles = yearArticles,
    )
}







