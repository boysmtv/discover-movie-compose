package com.mtv.app.movie.domain.user

import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.data.model.request.SplashRequest
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.UiErrorFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<SplashRequest, String>(dispatcher) {

    override fun execute(param: SplashRequest): Flow<ResourceFirebase<String>> {
        val deviceId = param.deviceInfo.deviceId
            ?: return flowOf(
                ResourceFirebase.Error(
                    UiErrorFirebase.NotFound(ErrorMessages.NOT_FOUND)
                )
            )

        param.initDate = System.currentTimeMillis().toString()

        return getDevice(deviceId, param)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getDevice(
        deviceId: String,
        param: SplashRequest
    ): Flow<ResourceFirebase<String>> {

        return dataSource.getDocumentByFields(
            collection = BuildConfig.FIREBASE_DEVICE_COLLECTION,
            data = mapOf("deviceId" to deviceId)
        ) { it }
            .flatMapLatest { result ->
                when (result) {
                    is ResourceFirebase.Loading ->
                        flowOf(ResourceFirebase.Loading)

                    is ResourceFirebase.Success ->
                        handleExistingDevice(result.data, param)

                    is ResourceFirebase.Error ->
                        handleNewDevice(param)
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleExistingDevice(
        data: Map<String, Any>,
        param: SplashRequest
    ): Flow<ResourceFirebase<String>> {

        val docId = data["id"]?.toString()
            ?: return flowOf(
                ResourceFirebase.Error(
                    UiErrorFirebase.Unknown(ErrorMessages.NOT_FOUND)
                )
            )

        return logDevice(param)
            .flatMapLatest {
                updateDevice(docId, param)
            }
            .map { ResourceFirebase.Success(docId) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleNewDevice(
        param: SplashRequest
    ): Flow<ResourceFirebase<String>> {

        return logDevice(param)
            .flatMapLatest {
                insertDevice(param)
            }
    }

    private fun logDevice(
        param: SplashRequest
    ): Flow<ResourceFirebase<String>> {
        return dataSource.addDocument(
            collection = BuildConfig.FIREBASE_DEVICE_LOG_COLLECTION,
            data = param.toMap()
        )
    }

    private fun updateDevice(
        docId: String,
        param: SplashRequest
    ): Flow<ResourceFirebase<Unit>> {
        return dataSource.updateDocument(
            collection = BuildConfig.FIREBASE_DEVICE_COLLECTION,
            documentId = docId,
            data = param.toMap()
        )
    }

    private fun insertDevice(
        param: SplashRequest
    ): Flow<ResourceFirebase<String>> {
        return dataSource.addDocument(
            collection = BuildConfig.FIREBASE_DEVICE_COLLECTION,
            data = param.toMap()
        )
    }
}