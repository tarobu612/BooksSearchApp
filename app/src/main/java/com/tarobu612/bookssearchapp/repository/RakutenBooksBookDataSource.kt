package com.tarobu612.bookssearchapp.repository

import com.tarobu612.bookssearchapp.model.RakutenBooksBookResponse
import com.tarobu612.bookssearchapp.model.ResponseResult

interface RakutenBooksBookDataSource {
    suspend fun getBooksByTitle(title: String): ResponseResult<RakutenBooksBookResponse, Throwable>

}
