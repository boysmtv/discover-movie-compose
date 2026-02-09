/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: AuthErrorCode.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.59
 */

package com.mtv.app.movie.common.login

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.UiErrorFirebase

fun mapFirebaseExceptionToUiError(e: Throwable): UiErrorFirebase = when (e) {
    is FirebaseAuthInvalidUserException -> UiErrorFirebase.NotFound(ErrorMessages.NOT_FOUND)
    is FirebaseAuthInvalidCredentialsException -> UiErrorFirebase.Unknown(ErrorMessages.INVALID_INPUT)
    is FirebaseTooManyRequestsException -> UiErrorFirebase.Unknown(ErrorMessages.TOO_MANY_LOGIN_ATTEMPTS)
    is FirebaseNetworkException -> UiErrorFirebase.Network(ErrorMessages.NETWORK_ERROR)
    is FirebaseAuthUserCollisionException -> UiErrorFirebase.Permission(ErrorMessages.USER_ALREADY_EXISTS)

    is FirebaseAuthException -> when (e.errorCode) {
        "ERROR_INVALID_EMAIL" -> UiErrorFirebase.Unknown(ErrorMessages.INVALID_EMAIL)
        "ERROR_USER_DISABLED" -> UiErrorFirebase.Permission(ErrorMessages.ACCESS_DENIED)
        "ERROR_USER_NOT_FOUND", "ERROR_EMAIL_NOT_FOUND" -> UiErrorFirebase.NotFound(ErrorMessages.NOT_FOUND)
        "ERROR_WRONG_PASSWORD" -> UiErrorFirebase.Unknown(ErrorMessages.INVALID_INPUT)
        "ERROR_TOO_MANY_REQUESTS" -> UiErrorFirebase.Unknown(ErrorMessages.TOO_MANY_LOGIN_ATTEMPTS)
        "ERROR_OPERATION_NOT_ALLOWED" -> UiErrorFirebase.Permission(ErrorMessages.AUTH_METHOD_DISABLED)
        "ERROR_REQUIRES_RECENT_LOGIN", "ERROR_USER_TOKEN_EXPIRED" -> UiErrorFirebase.Permission(ErrorMessages.SESSION_EXPIRED)
        "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> UiErrorFirebase.Permission(ErrorMessages.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL)
        "ERROR_INTERNAL_ERROR", "ERROR_INVALID_API_KEY", "ERROR_APP_NOT_AUTHORIZED" -> UiErrorFirebase.Unknown(ErrorMessages.SERVER_ERROR)
        else -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
    }

    else -> UiErrorFirebase.Unknown(ErrorMessages.GENERIC_ERROR)
}
