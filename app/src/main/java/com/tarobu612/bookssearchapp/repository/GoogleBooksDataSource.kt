package com.tarobu612.bookssearchapp.repository

import com.tarobu612.bookssearchapp.model.GoogleBooksResponse
import com.tarobu612.bookssearchapp.model.ResponseResult

interface GoogleBooksDataSource {
    suspend fun getBooksByKeyword(word: String): ResponseResult<GoogleBooksResponse, Throwable>

}
