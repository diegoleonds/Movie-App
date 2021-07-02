package com.example.moviesapi.domain.error

import com.example.moviesapi.data.model.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}