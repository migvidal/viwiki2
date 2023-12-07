package com.migvidal.viwiki2.adapters

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.ui.UiDayArticle
import com.migvidal.viwiki2.ui.UiDayImage
import com.migvidal.viwiki2.ui.UiFeaturedArticle

fun DatabaseArticle.toUiArticle(
    thumbnail: DatabaseImage?,
) = UiDayArticle(
    views = views,
    normalizedTitle = normalizedTitle,
    description = description,
    extract = extract,
    thumbnail = thumbnail
)

fun DatabaseDayImage.toUiDayImage(
    thumbnail: DatabaseImage,
    fullSizeImage: DatabaseImage,
    description: DatabaseDescription
) = UiDayImage(
    id = dayImageId,
    title = title,
    thumbnail = thumbnail,
    fullSizeImage = fullSizeImage,
    description = description.text,
)

fun DatabaseArticle.toUiFeaturedArticle(
    thumbnail: DatabaseImage,
    fullSizeImage: DatabaseImage,
) = UiFeaturedArticle(
    id = articleId,
    thumbnail = thumbnail,
    fullSizeImage = fullSizeImage,
    normalizedTitle = normalizedTitle,
    description = description,
    extract = extract,
)







