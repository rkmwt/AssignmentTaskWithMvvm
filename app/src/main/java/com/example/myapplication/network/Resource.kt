package com.example.myapplication.network

sealed class Resource<out T> {

    data class Success<out T>(val value: T): Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ): Resource<Nothing>()
    class TokenExpired(): Resource<Nothing>()
}