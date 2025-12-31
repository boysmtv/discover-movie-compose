/*
 * Project: Boys.mtv@gmail.com
 * File: CheckUseCase.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.53
 */

package com.mtv.app.movie.feature.check

import com.mtv.app.movie.data.remote.tmdb.TmdbApi
import com.mtv.app.movie.feature.check.model.CheckRequest
import com.mtv.app.movie.feature.check.model.CheckResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.usecase.BaseUseCase
import com.mtv.based.core.network.utils.NetworkRepository
import com.mtv.based.core.network.utils.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CheckUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<CheckRequest, CheckResponse>(dispatcher, CheckResponse::class) {

    override suspend fun execute(param: CheckRequest): NetworkResponse {
        return repository.post(TmdbApi.postCheck, param)
    }
}
