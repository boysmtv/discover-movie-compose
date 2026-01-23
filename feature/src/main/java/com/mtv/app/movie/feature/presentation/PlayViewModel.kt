package com.mtv.app.movie.feature.presentation

import androidx.lifecycle.SavedStateHandle
import com.mtv.app.core.provider.based.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val key: String = checkNotNull(savedStateHandle["key"])

    fun getKey(): String = key

}