/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: UploadProfilePhotoUseCase.kt
 *
 * Last modified by Dedy Wijaya on 05/02/26 11.19
 */

package com.mtv.app.movie.domain.user

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadProfilePhotoUseCase @Inject constructor(
    private val storage: FirebaseStorage,
    private val sessionManager: SessionManager,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<Uri, String>(dispatcher) {

    override fun execute(param: Uri): Flow<ResourceFirebase<String>> = flow {
        val uid = sessionManager.getUid() ?: throw IllegalStateException("No UID")

        val ref = storage.reference
            .child("profile")
            .child("$uid.jpg")

        ref.putFile(param).await()

        val downloadUrl = ref.downloadUrl.await().toString()

        emit(ResourceFirebase.Success(downloadUrl))
    }
}
