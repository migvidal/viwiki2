package com.migvidal.viwiki2.data.database

import com.migvidal.viwiki2.data.database.entities.DayImage
import com.migvidal.viwiki2.data.database.entities.FeaturedArticle
import com.migvidal.viwiki2.data.database.entities.MostRead
import com.migvidal.viwiki2.data.database.entities.OnThisDay


data class DayData(
    val featuredArticle: FeaturedArticle,
    val mostRead: MostRead,
    val image: DayImage,
    val onThisDay: List<OnThisDay>,
)







