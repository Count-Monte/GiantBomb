package com.elsa.giantbomb.entities

import java.lang.Exception

/**
 * Sealed class with status of [Success] and [Failure]
 */
sealed class DataResult<T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Failure<T>(val exception: Exception): DataResult<T>()
}