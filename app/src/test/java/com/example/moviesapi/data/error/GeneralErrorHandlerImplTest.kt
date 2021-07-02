package com.example.moviesapi.data.error

import com.example.moviesapi.data.model.ErrorEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class GeneralErrorHandlerImplTest {
    val errorHandler = GeneralErrorHandlerImpl()
    val mockkHttpException = mockk<HttpException>()
    val mockkIOException = mockk<IOException>()

    @Test
    fun shouldReturnNotFoundWhenReceiveSameError() {
        val expectedError = ErrorEntity.NotFound
        every { mockkHttpException.code() } returns HttpURLConnection.HTTP_NOT_FOUND

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldNotReturnNotFoundWhenReceiveDiferentError() {
        val expectedError = ErrorEntity.NotFound
        every { mockkHttpException.code() } returns -1

        assertNotEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldReturnForbiddenWhenReceiveSameError() {
        val expectedError = ErrorEntity.AccessDenied
        every { mockkHttpException.code() } returns HttpURLConnection.HTTP_FORBIDDEN

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldNotReturnForbiddenWhenReceiveDiferentError() {
        val expectedError = ErrorEntity.AccessDenied
        every { mockkHttpException.code() } returns -1

        assertNotEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldReturnUnavailableWhenReceiveSameError() {
        val expectedError = ErrorEntity.ServiceUnavailable
        every { mockkHttpException.code() } returns HttpURLConnection.HTTP_UNAVAILABLE

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldNotReturnUnavailableWhenReceiveDiferentError() {
        val expectedError = ErrorEntity.ServiceUnavailable
        every { mockkHttpException.code() } returns -1

        assertNotEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldReturnUnknownWhenErrorIsNotDefined() {
        val expectedError = ErrorEntity.Unknown
        every { mockkHttpException.code() } returns -1

        assertEquals(expectedError, errorHandler.getError(mockkHttpException))
    }

    @Test
    fun shouldReturnNetworkErrorWhenReceiveIOException() {
        val expectedError = ErrorEntity.Network
        assertEquals(expectedError, errorHandler.getError(mockkIOException))
    }

    @Test
    fun shouldNotReturnNetworkErrorWhenNotReceiveIOException() {
        val expectedError = ErrorEntity.Network
        every { mockkHttpException.code() } returns -1

        assertNotEquals(expectedError, errorHandler.getError(mockkHttpException))
    }
}