/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordUseCase.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 16.33
 */

package com.mtv.app.movie.domain.user

import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.safeToDataClass
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PasswordUseCase<T : Any> @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<String, T>(dispatcher) {

    override fun execute(param: String): Flow<ResourceFirebase<T>> =
        dataSource.getDocument(
            collection = config.defaultCollection,
            documentId = param,
            mapper = { it.safeToDataClass(clazz) }
        )

}
