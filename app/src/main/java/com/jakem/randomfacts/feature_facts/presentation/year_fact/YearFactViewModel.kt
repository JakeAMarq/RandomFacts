package com.jakem.randomfacts.feature_facts.presentation.year_fact

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jakem.randomfacts.R
import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.domain.use_cases.GetYearFactUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class YearFactViewModel @AssistedInject constructor(
    @Assisted year: Int,
    private val getYearFact: GetYearFactUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(year: Int): YearFactViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            year: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(year) as T
            }
        }
    }

    private val _state = mutableStateOf(YearFactScreenState())
    val state: State<YearFactScreenState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getYearFactJob: Job? = null

    init {
        onGetYearFact(year)
    }

    private fun onGetYearFact(year: Int) {
        getYearFactJob?.cancel()
        getYearFactJob = viewModelScope.launch {
            getYearFact(year)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                fact = result.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                fact = result.data,
                                isLoading = false
                            )

                            _eventFlow.emit(UiEvent.ShowToast(result.messageId ?: R.string.error_unknown))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                fact = result.data,
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UiEvent {
        data class ShowToast(@StringRes val messageId: Int): UiEvent()
    }

}