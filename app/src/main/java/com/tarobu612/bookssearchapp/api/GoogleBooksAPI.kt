package com.tarobu612.bookssearchapp.api

import com.tarobu612.bookssearchapp.GOOGLE_BOOKS_API_KEY
import com.tarobu612.bookssearchapp.model.GoogleBooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksAPI {

    @GET("v1/volumes")
    suspend fun searchBooksByKeyword(
        @Query("q")
        keyword: String,

        @Query("key")
        apiKey: String = GOOGLE_BOOKS_API_KEY
    ): Response<GoogleBooksResponse>

}
