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
        @Json(name = "pageid")
        val pageId: Int,
        val ns: Int,
        val extract: String,
        val title: String,
        @Json(name = "pageimage")
        val pageImage: String?,
        val original: NetworkImage?,
    )
}