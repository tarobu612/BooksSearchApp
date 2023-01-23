package com.tarobu612.bookssearchapp.repository

import com.tarobu612.bookssearchapp.RAKUTEN_BOOKS_BASE_URL
import com.tarobu612.bookssearchapp.api.ApiRequester
import com.tarobu612.bookssearchapp.api.RakutenBooksBookAPI
import com.tarobu612.bookssearchapp.model.RakutenBooksBookResponse
import com.tarobu612.bookssearchapp.model.ResponseResult

class RakutenBooksBookRepository : RakutenBooksBookDataSource {

    override suspend fun getBooksByTitle(
        title: String
    ): ResponseResult<RakutenBooksBookResponse, Throwable> {
        val result = runCatching {
            ApiRequester.create<RakutenBooksBookAPI>(RAKUTEN_BOOKS_BASE_URL)
                .searchBooksByTitle(title)
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
        private var INSTANCE: RakutenBooksBookRepository? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: synchronized(RakutenBooksBookRepository::class.java) {
            RakutenBooksBookRepository()
        }.also { INSTANCE = it }

    }

}
