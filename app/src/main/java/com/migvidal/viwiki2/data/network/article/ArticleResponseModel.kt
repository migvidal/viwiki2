package com.migvidal.viwiki2.data.network.article


import com.migvidal.viwiki2.data.network.NetworkImage
import com.squareup.moshi.Json

data class ArticleResponseModel(
    @Json(name = "batchcomplete")
    val batchComplete: Boolean,
    val query: NetworkQuery,
)

data class NetworkQuery(
    val pages: List<NetworkPage>
) {
    data class NetworkPage(
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