package com.mtv.app.movie.domain.usecase

import com.mtv.app.movie.common.utils.toDataClass
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.datasource.FirebaseDataSource
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.firebase.usecase.BaseFirebaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginFirebaseUseCase<T : Any> @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<Map<String, Any>, T>(dispatcher) {

    override fun execute(param: Map<String, Any>): Flow<FirebaseResult<T>> {
        return dataSource.getDocumentByFields(
            collection = config.defaultCollection,
            data = param,
            mapper = { map -> map.toDataClass(clazz) }
        )
    }

}