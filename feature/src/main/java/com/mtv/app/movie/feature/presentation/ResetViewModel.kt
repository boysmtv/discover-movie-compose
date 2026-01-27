package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.domain.user.ResetUseCase
import com.mtv.app.movie.feature.event.reset.ResetStateListener
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.UiErrorFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ResetViewModel @Inject constructor(
    private val resetUseCase: ResetUseCase,
) : BaseViewModel() {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    private val _uiState = MutableStateFlow(ResetStateListener())
    val uiState: StateFlow<ResetStateListener> = _uiState

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _uiState.update {
                it.copy(resetPasswordState = ResourceFirebase.Error(UiErrorFirebase.NotFound(ErrorMessages.NOT_FOUND)))
            }
            return
        }

        launchFirebaseUseCase(
            target = _uiState.valueFlowOf(
                get = { it.resetPasswordState },
                set = { state -> copy(resetPasswordState = state) }
            ),
            block = {
                resetUseCase(param = email)
            }
        )
    }
}