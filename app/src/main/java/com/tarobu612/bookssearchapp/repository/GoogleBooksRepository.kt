package com.tarobu612.bookssearchapp.repository

import com.tarobu612.bookssearchapp.GOOGLE_BOOKS_BASE_URL
import com.tarobu612.bookssearchapp.api.ApiRequester
import com.tarobu612.bookssearchapp.api.GoogleBooksAPI
import com.tarobu612.bookssearchapp.model.GoogleBooksResponse
import com.tarobu612.bookssearchapp.model.ResponseResult

class GoogleBooksRepository : GoogleBooksDataSource {

    override suspend fun getBooksByKeyword(
        word: String
    ): ResponseResult<GoogleBooksResponse, Throwable> {
        val result = runCatching {
            ApiRequester.create<GoogleBooksAPI>(GOOGLE_BOOKS_BASE_URL).searchBooksByKeyword(word)
        }

        val exception = result.exceptionOrNull()
        exception?.let {
            return ResponseResult.Failure(it)
        }

        val response = result.getOrNull()
        if (response?.body() == null) {
            return ResponseResult.Failure(Exception("response is null"))
        }

        if (response.isSuccessful) {
            return ResponseResult.Success(response.body()!!)
        }

        return ResponseResult.Failure(Exception("not excepted result"))
    }

    companion object {
        private var INSTANCE: GoogleBooksRepository? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: synchronized(GoogleBooksRepository::class.java) {
            GoogleBooksRepository()
        }.also { INSTANCE = it }

    }

}
