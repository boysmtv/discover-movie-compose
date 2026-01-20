package com.mtv.app.movie.domain.user

import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<Map<String, Any>, String>(dispatcher) {

    override fun execute(param: Map<String, Any>): Flow<ResourceFirebase<String>> {
        return dataSource.addDocument(
            collection = config.defaultCollection,
            data = param
        )
    }

}