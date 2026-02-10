/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordUseCase.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 16.33
 */

package com.mtv.app.movie.domain.user

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.movie.data.model.request.PasswordRequest
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.mapFirebaseExceptionToUiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PasswordUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<PasswordRequest, Unit>(dispatcher) {

    override fun execute(
        param: PasswordRequest
    ): Flow<ResourceFirebase<Unit>> = flow {
        emit(ResourceFirebase.Loading)

        val user = FirebaseAuth.getInstance().currentUser
            ?: throw FirebaseAuthInvalidUserException(
                "ERROR_USER_NOT_FOUND",
                ErrorMessages.NOT_FOUND
            )

        val credential = EmailAuthProvider.getCredential(
            param.email,
            param.oldPassword
        )

        user.reauthenticate(credential).await()
        user.updatePassword(param.newPassword).await()
        emit(ResourceFirebase.Success(Unit))
    }.catch { throwable ->
        emit(
            ResourceFirebase.Error(
                mapFirebaseExceptionToUiError(throwable)
            )
        )
    }

}