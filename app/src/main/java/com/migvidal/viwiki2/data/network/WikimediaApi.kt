package com.migvidal.viwiki2.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * WikiMedia API. Used to get the "today" data.
 */

/**
 * Base URL for the Wikimedia API
 */
private const val WikimediaBaseUrl: String = "https://api.wikimedia.org/"

/**
 * HTTP client
 */
private val okHttpClient = OkHttpClient.Builder()
    .build()

/**
 * Retrofit client, for creating the API service
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(WikimediaBaseUrl)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(ApiCommons.moshi))
    .build()

/**
 * Interface for Retrofit to handle queries
 */
interface WikiMediaApiService {
    @GET(
        "/feed/v1/wikipedia/en/featured/" +
                "{yyyy}/{mm}/{dd}"
    )
    /**
     * Fetches the "today" response for a certain date
     * @param yyyy The year
     * @param mm The month
     * @param dd The day
     */
    suspend fun getFeatured(
        @Path("yyyy") yyyy: String,
        @Path("mm") mm: String,
        @Path("dd") dd: String
    ): NetworkDayResponse
}


object WikiMediaApiImpl {
    /**
     * Service for the WikiMedia API
     */
    val wikiMediaApiService: WikiMediaApiService by lazy {
        retrofit.create(WikiMediaApiService::class.java)
    }
}