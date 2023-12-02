package com.migvidal.viwiki2.data.network.search

import com.squareup.moshi.Json

data class SearchResponseModel(
    @Json(name = "batchcomplete")
    val batchComplete: String = "",
    val `continue`: Continue? = null,
    val query: Query? = null,
)

data class Continue(
    val `continue`: String,
    @Json(name = "sroffset")
    val srOffset: Int
)

data class Query(
    val search: List<Search>,
    @Json(name = "searchinfo")
    val searchInfo: SearchInfo
) {
    data class SearchInfo(
        @Json(name = "totalhits")
        val totalHits: Int
    )

    data class Search(
        val ns: Int,
        @Json(name = "pageid")
        val pageId: Int,
        val size: Int,
        val snippet: String,
        val timestamp: String,
        val title: String,
        @Json(name = "wordcount")
        val wordCount: Int
    )
}

