package com.mtv.app.movie.domain.user

import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.movie.common.BuildConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<Map<String, Any>, String>(dispatcher) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(param: Map<String, Any>): Flow<ResourceFirebase<String>> {
        return dataSource.isExistByFields(
            collection = BuildConfig.FIREBASE_DEVICE_COLLECTION,
            data = param
        ).flatMapLatest { isExist ->
            if (isExist) {
                flow {
                    emit(ResourceFirebase.Success(EMPTY_STRING))
                }
            } else {
                dataSource.addDocument(
                    collection = BuildConfig.FIREBASE_DEVICE_COLLECTION,
                    data = param
                )
            }
        }
    }


}