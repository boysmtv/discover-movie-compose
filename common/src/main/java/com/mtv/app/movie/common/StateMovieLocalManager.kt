/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: StateMovieLocalManager.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 10.06
 */

package com.mtv.app.movie.common

import com.mtv.based.core.network.utils.ErrorMessages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

inline fun <T> MutableStateFlow<T>.runStateWithActionMovieLocalManager(
    crossinline block: () -> Unit,
    crossinline reducer: (T, StateMovieResult) -> T,
    crossinline onSuccess: () -> Unit = {},
    crossinline onError: (Throwable) -> Unit = {}
) {
    runCatching {
        block()
    }.onSuccess {
        update { reducer(it, StateMovieResult.Success) }
        onSuccess()
    }.onFailure { throwable ->
        update {
            reducer(
                it,
                StateMovieResult.Error(
                    throwable.message ?: ErrorMessages.GENERIC_ERROR
                )
            )
        }
        onError(throwable)
    }
}

inline fun runSilentMovieLocalManager(
    crossinline block: () -> Unit,
    crossinline onError: (Throwable) -> Unit = {}
) {
    runCatching {
        block()
    }.onFailure(onError)
}

