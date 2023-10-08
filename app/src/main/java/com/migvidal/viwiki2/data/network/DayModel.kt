package com.migvidal.viwiki2.data.network

import com.migvidal.viwiki2.data.database.entities.Article
import com.squareup.moshi.Json

data class Image(
    val source: String,
    val width: Int,
    val height: Int,
)

data class DayResponse(
    @Json(name = "tfa")
    val featuredArticle: FeaturedArticle?,

    @Json(name = "mostread")
    val mostRead: MostRead?,

    val image: DayImage?,

    @Json(name = "onthisday")
    val onThisDay: List<OnThisDay>,
)

data class FeaturedArticle(
    val type: String,
    val title: String,
    @Json(name = "displaytitle")
    val displayTitle: String,
    @Json(name = "originalimage")
    val originalImage: Image,
    val thumbnail: Image,
    val description: String,
    val extract: String,
    @Json(name = "normalizedtitle")
    val normalizedTitle: String,
)

data class MostRead(
    val date: String,
    val articles: List<Article>,
)

class OnThisDay

data class DayImage(
    val title: String,
    val thumbnail: Image,
    val image: Image,
    @Json(name = "filepage")
    val filePage: String,
    val description: Description,
) {
    data class Description(
        val text: String,
        val lang: String,
    )
}
