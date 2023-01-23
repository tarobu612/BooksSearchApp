package com.tarobu612.bookssearchapp.model

sealed class ResponseResult<out T, out E> {
    data class Success<out T : Any?>(val value: T) : ResponseResult<T, Nothing>()

    data class Failure<out E>(val error: E) : ResponseResult<Nothing, E>()
    
}