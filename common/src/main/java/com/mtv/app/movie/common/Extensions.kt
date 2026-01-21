package com.mtv.app.movie.common

import kotlinx.coroutines.flow.MutableStateFlow

/** HELPER */
fun <S, T> MutableStateFlow<S>.valueFlowOf(
    get: (S) -> T,
    set: S.(T) -> S
): MutableStateFlow<T> {
    val parent = this
    return object : MutableStateFlow<T> by MutableStateFlow(get(parent.value)) {
        override var value: T
            get() = get(parent.value)
            set(v) {
                parent.value = parent.value.set(v)
            }
    }
}