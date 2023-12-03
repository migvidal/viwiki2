package com.migvidal.viwiki2.data.network.search

import com.migvidal.viwiki2.data.network.ApiCommons
import com.migvidal.viwiki2.data.network.article.ArticleResponseModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to get data from Wikipedia.
 */

/**
 * Base URL for the Wikipedia API
 */
private const val WikipediaBaseUrl: String = "https://en.wikipedia.org/"

/**
 * Adds basic parameters
 */
private val paramsInterceptor = Interceptor { chain ->
    var request = chain.request()
    val newUrl = request.url.newBuilder()
        .addPathSegments("w/api.php")
        .addQueryParameter("action", "query")
        .addQueryParameter("format", "json")
        .addQueryParameter("list", "search")
        .addQueryParameter("srlimit", "20")
        .build()
    request = request.newBuilder()
        .url(newUrl)
        .build()
    chain.proceed(request)
}

/**
 * HTTP client
 */
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(paramsInterceptor)
    .addInterceptor(ApiCommons.loggingInterceptor)
    .build()

/**
 * Retrofit client, for creating the API service
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(WikipediaBaseUrl)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(ApiCommons.moshi))
    .build()

/**
 * Interface for Retrofit to handle queries
 */
interface WikipediaApiService {
    /**
     * Fetches the search results for a query
     * @param query Search query
     */
    @GET("/w/api.php")
    suspend fun getSearchResults(@Query("srsearch") query: String): SearchResponseModel

    /**
     * Fetches data from an article by its ID
     */
    @GET("/w/api.php")
    suspend fun getArticleResponse(
        @Query("titles") title: String,
        @Query("prop") prop: String = "extracts",
        @Query("exsentences") exsentences: Int = 30,
        @Query("explaintext") explaintext: Int = 1,
        @Query("formatversion") formatVersion: Int = 2
    ): ArticleResponseModel
}


object WikipediaApiImpl {
    /**
     * Service for the Wikipedia API
     */
    val wikipediaApiService: WikipediaApiService by lazy {
        retrofit.create(WikipediaApiService::class.java)
    }
}