package com.example.moviesapi.data.model

import com.example.moviesapi.R

/**
 * Used on repository to handle http errors and to expose them for other layers to
 * be able to do this too
 */
sealed class ErrorEntity(val messageResource: Int) {
    object Network : ErrorEntity(R.string.network_error)

    object NotFound : ErrorEntity(R.string.not_found_error)

    object AccessDenied : ErrorEntity(R.string.access_denied_error)

    object ServiceUnavailable : ErrorEntity(R.string.service_unavailable_error)

    object Unknown : ErrorEntity(R.string.unknown_error)
}