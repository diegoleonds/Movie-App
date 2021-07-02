package com.example.moviesapi.data.model
/**
 * Used as a return from repository, to in case of success just send the data to other layers,
 * or in case of failure send the error
 */
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: ErrorEntity) : Result<T>()
}