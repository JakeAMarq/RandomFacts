package com.jakem.randomfacts.core.util

import androidx.annotation.StringRes

sealed class Resource<T>(
    val data: T? = null,
    @StringRes val messageId: Int? = null
) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(@StringRes messageId: Int?, data: T? = null): Resource<T>(data, messageId)
}