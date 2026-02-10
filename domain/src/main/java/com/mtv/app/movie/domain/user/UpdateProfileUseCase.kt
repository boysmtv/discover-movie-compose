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
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.safeToDataClass
import com.mtv.app.movie.data.model.request.UpdateProfileRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.mapFirebaseExceptionToUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateProfileUseCase<T : Any> @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<UpdateProfileRequest, T>(dispatcher) {

    override fun execute(param: UpdateProfileRequest): Flow<ResourceFirebase<T>> = flow {

        emit(ResourceFirebase.Loading)

        val user = FirebaseAuth.getInstance().currentUser
            ?: throw FirebaseAuthInvalidUserException(
                "ERROR_USER_NOT_FOUND",
                ErrorMessages.NOT_FOUND
            )

        val credential = EmailAuthProvider.getCredential(
            param.email,
            param.password
        )

        user.reauthenticate(credential).await()

        dataSource.updateDocument(
            collection = config.defaultCollection,
            documentId = param.uid,
            data = mapOf(
                "name" to param.name,
                "phone" to param.phone,
                "photo" to param.photo
            )
        ).collect { result ->
            if (result is ResourceFirebase.Error) {
                emit(result)
                return@collect
            }
        }

        emitAll(
            getUserByUid(param.uid)
                .filterNot { it is ResourceFirebase.Loading }
        )
    }.catch { throwable ->
        emit(
            ResourceFirebase.Error(
                mapFirebaseExceptionToUiError(throwable)
            )
        )
    }

    private fun getUserByUid(uid: String): Flow<ResourceFirebase<T>> =
        dataSource.getDocument(
            collection = config.defaultCollection,
            documentId = uid,
            mapper = { it.safeToDataClass(clazz) }
        )
}
