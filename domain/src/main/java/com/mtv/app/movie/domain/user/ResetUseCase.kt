package com.mtv.app.movie.domain.user

import com.google.firebase.auth.FirebaseAuth
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ResetUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<String, Unit>(dispatcher) {

    override fun execute(param: String): Flow<ResourceFirebase<Unit>> = flow {
        firebaseAuth.sendPasswordResetEmail(param).await()
        emit(ResourceFirebase.Success(Unit))
    }

}
