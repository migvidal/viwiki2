package com.migvidal.viwiki2.data.network

import com.squareup.moshi.Json

data class NetworkImage(
    val source: String,
    val width: Int,
    val height: Int,
)

data class NetworkDayResponse(
    @Json(name = "tfa")
    val featuredArticle: NetworkFeaturedArticle?,

    @Json(name = "mostread")
    val mostRead: NetworkMostRead?,

    val image: NetworkDayImage?,

    @Json(name = "onthisday")
    val onThisDay: List<NetworkOnThisDay>?,
)

data class NetworkFeaturedArticle(
    val type: String,
    val title: String,
    @Json(name = "displaytitle")
    val displayTitle: String,
    @Json(name = "originalimage")
    val originalImage: NetworkImage,
    val thumbnail: NetworkImage,
    val description: String,
    val extract: String,
    @Json(name = "normalizedtitle")
    val normalizedTitle: String,
)

data class NetworkMostRead(
    val date: String,
    val articles: List<NetworkArticle>,
)

data class NetworkArticle(
    val views: Int?,
    @Json(name = "normalizedtitle")
    val normalizedTitle: String,
    val description: String?,
    val extract: String,
    val thumbnail: NetworkImage?,
)

data class NetworkOnThisDay(
    val text: String,
    val year: Int,

)

data class NetworkDayImage(
    val title: String,
    val thumbnail: NetworkImage,
    val image: NetworkImage,
    @Json(name = "file_page")
    val filePage: String,
    val description: NetworkDescription,
) {
    data class NetworkDescription(
        val text: String,
        val lang: String,
    )
}
