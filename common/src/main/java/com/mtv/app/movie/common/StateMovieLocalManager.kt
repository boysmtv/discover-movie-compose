/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: StateMovieLocalManager.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 10.06
 */

package com.mtv.app.movie.common

import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.UiError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

inline fun <T> MutableStateFlow<T>.runStateMovieLocalManager(
    crossinline block: () -> Unit,
    crossinline reducer: (T, Resource<Unit>) -> T,
    crossinline onSuccess: () -> Unit = {},
    crossinline onError: (Throwable) -> Unit = {}
) {
    runCatching {
        block()
    }.onSuccess {
        update { reducer(it, Resource.Success(Unit)) }
        onSuccess()
    }.onFailure { throwable ->
        update {
            reducer(
                it,
                Resource.Error(
                    UiError.Unknown(
                        throwable.message ?: ErrorMessages.GENERIC_ERROR
                    )
                )
            )
        }
        onError(throwable)
    }
}