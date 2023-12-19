package com.migvidal.viwiki2.data.network.day

import com.migvidal.viwiki2.data.network.NetworkImage
import com.squareup.moshi.Json

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
    @Json(name = "pageid")
    val pageId: Int,
    @Json(name = "originalimage")
    val originalImage: NetworkImage?,
    val thumbnail: NetworkImage?,
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
    @Json(name = "pageid")
    val pageId: Int,
    val thumbnail: NetworkImage?,
    @Json(name = "originalimage")
    val originalImage: NetworkImage?,
)

data class NetworkOnThisDay(
    val text: String,
    val year: Int,
    val pages: List<NetworkArticle>,
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
