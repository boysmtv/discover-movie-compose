package com.mtv.app.movie.domain.user

import com.google.firebase.auth.FirebaseAuth
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase<T : Any> @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<LoginRequest, T>(dispatcher) {

    override fun execute(param: LoginRequest): Flow<ResourceFirebase<T>> {
        TODO("Not yet implemented")
    }

}