package com.migvidal.viwiki2.data.network.search

import com.migvidal.viwiki2.data.network.ApiCommons
import com.migvidal.viwiki2.data.network.day.NetworkDayResponse
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
 * HTTP client
 */
private val okHttpClient = OkHttpClient.Builder()
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
    @GET("/w/rest.php/v1/search/page")
    /**
     * Fetches the search results for a query
     * @param query Search query
     */
    suspend fun getSearchResults(@Query("q") query: String): NetworkDayResponse
}


object WikipediaApiImpl {
    /**
     * Service for the Wikipedia API
     */
    val wikipediaApiService: WikipediaApiService by lazy {
        retrofit.create(WikipediaApiService::class.java)
    }
}