package com.jakem.randomfacts.feature_facts.presentation.fact_list

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakem.randomfacts.R
import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.domain.use_cases.GetNumberFactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactListViewModel @Inject constructor(
    private val getNumberFacts: GetNumberFactsUseCase
) : ViewModel() {

    companion object {
        private const val FACT_LIST_START = 1
        private const val FACT_LIST_END = 50
    }

    private val _state = mutableStateOf(FactListScreenState())
    val state: State<FactListScreenState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getFactsJob: Job? = null

    init {
        onGetFacts(FACT_LIST_START, FACT_LIST_END)
    }

    private fun onGetFacts(start: Int, end: Int = start) {
        getFactsJob?.cancel()
        getFactsJob = viewModelScope.launch {
            getNumberFacts(start, end)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                facts = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                facts = result.data ?: emptyList(),
                                isLoading = false
                            )

                            _eventFlow.emit(UiEvent.ShowToast(result.messageId ?: R.string.error_unknown))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                facts = result.data ?: emptyList(),
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