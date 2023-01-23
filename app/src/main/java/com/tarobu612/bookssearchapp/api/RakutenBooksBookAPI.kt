package com.tarobu612.bookssearchapp.api

import com.tarobu612.bookssearchapp.RAKUTEN_BOOKS_BOOK_APPLICATION_ID
import com.tarobu612.bookssearchapp.model.RakutenBooksBookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RakutenBooksBookAPI {

    @GET("Search/20170404")
    suspend fun searchBooksByTitle(
        @Query("title")
        title: String,

        @Query("applicationId")
        apiKey: String = RAKUTEN_BOOKS_BOOK_APPLICATION_ID,
    ): Response<RakutenBooksBookResponse>

}
