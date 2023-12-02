package com.migvidal.viwiki2.data.network.search

import com.migvidal.viwiki2.data.network.ApiCommons
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * WikiMedia API. Used to get the "today" data.
 */

/**
 * Base URL for the Wikimedia API
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
    @GET("/w/api.php")
    /**
     * Fetches the search results for a query
     * @param query Search query
     */
    suspend fun getSearchResults(@Query("srsearch") query: String): SearchResponseModel
}


object WikipediaApiImpl {
    /**
     * Service for the Wikipedia API
     */
    val wikipediaApiService: WikipediaApiService by lazy {
        retrofit.create(WikipediaApiService::class.java)
    }
}