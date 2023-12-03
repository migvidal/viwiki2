package com.migvidal.viwiki2.data.network.article


import com.migvidal.viwiki2.data.network.NetworkImage
import com.squareup.moshi.Json

data class ArticleResponseModel(
    @Json(name = "batchcomplete")
    val batchComplete: Boolean? = null,
    val query: NetworkQuery? = null,
)

data class NetworkQuery(
    val pages: List<NetworkArticle>
) {
    data class NetworkArticle(
        val extract: String,
        val ns: Int,
        @Json(name = "pageid")
        val pageId: Int,
        @Json(name = "pageimage")
        val pageImage: String,
        val thumbnail: NetworkImage,
        val title: String
    )
}