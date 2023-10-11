package com.migvidal.viwiki2.data.database

import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay


data class DayData(
    val databaseFeaturedArticle: DatabaseFeaturedArticle?,
    val databaseMostReadArticles: List<DatabaseArticle>?,
    val image: DatabaseDayImage?,
    val databaseOnThisDay: List<DatabaseOnThisDay>?,
)







