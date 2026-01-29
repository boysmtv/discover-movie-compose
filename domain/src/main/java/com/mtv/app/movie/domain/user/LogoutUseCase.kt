/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LogoutUseCase.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.53
 */

package com.mtv.app.movie.domain.user

import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.safeToDataClass
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.data.model.request.LogoutRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase<T : Any> @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<LogoutRequest, T>(dispatcher) {

    override fun execute(param: LogoutRequest): Flow<ResourceFirebase<T>> {
        return dataSource.getDocumentByFields(
            collection = config.defaultCollection,
            data = param.toMap(),
            mapper = { map -> map.safeToDataClass(clazz) }
        )
    }
}
