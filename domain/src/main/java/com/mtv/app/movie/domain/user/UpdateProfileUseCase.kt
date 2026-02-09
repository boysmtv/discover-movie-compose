/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: UpdateProfileUseCase.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.59
 */

package com.mtv.app.movie.domain.user

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.common.login.mapFirebaseExceptionToUiError
import com.mtv.app.movie.data.model.request.UpdateProfileRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<UpdateProfileRequest, Unit>(dispatcher) {

    override fun execute(param: UpdateProfileRequest): Flow<ResourceFirebase<Unit>> = flow {

        emit(ResourceFirebase.Loading)

        val user = FirebaseAuth.getInstance().currentUser
            ?: throw FirebaseAuthException("ERROR_USER_NOT_FOUND", ErrorMessages.NOT_FOUND)

        val credential = EmailAuthProvider.getCredential(param.email, param.password)

        user.reauthenticate(credential).await()

        emitAll(
            dataSource.updateDocument(
                collection = config.defaultCollection,
                documentId = param.uid,
                data = mapOf(
                    "name" to param.name,
                    "phone" to param.phone,
                    "photo" to param.photo
                )
            )
        )

    }.catch { throwable ->
        emit(
            ResourceFirebase.Error(
                mapFirebaseExceptionToUiError(throwable)
            )
        )
    }
}
