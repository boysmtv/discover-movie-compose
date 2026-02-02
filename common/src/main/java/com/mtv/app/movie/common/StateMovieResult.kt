/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: StateMovieAction.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 09.43
 */

package com.mtv.app.movie.common

sealed class StateMovieResult {
    object None : StateMovieResult()
    object Success : StateMovieResult()
    data class Error(val message: String) : StateMovieResult()
}