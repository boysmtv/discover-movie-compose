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

class CheckUseCase<T : Any> @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<Map<String, Any>, T>(dispatcher) {

    override fun execute(param: Map<String, Any>): Flow<ResourceFirebase<T>> {
        return dataSource.getDocumentByFields(
            collection = config.defaultCollection,
            data = param,
            mapper = { map -> map.safeToDataClass(clazz) }
        )
    }

}