package com.shalatan.entertainmentapp.presentation.ui

import androidx.lifecycle.viewModelScope
import com.app.base.BaseViewModel
import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.database.DatabaseRepository
import com.shalatan.entertainmentapp.network.ApiHelperImpl
import com.shalatan.entertainmentapp.network.NetworkRepository
import com.shalatan.entertainmentapp.presentation.contracts.MainContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val TAG = "app_log"

@HiltViewModel
class NewMainViewModel @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl,
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) : BaseViewModel<Event, MainUIState, UIEffect>() {

    override fun createInitialState(): MainUIState {
        return MainUIState(homeScreenData = HomeScreenData())
    }

    init {
        Timber.d("HAHA: stateData1: ${currentState.homeScreenData}")
        viewModelScope.launch {
            fetchMovies()
        }
    }

    private suspend fun fetchMovies() {
        when (val incomingMovies = apiHelperImpl.getNowPlayingMovies()) {
            is StateFullResult.Failure -> Unit
            is StateFullResult.Loading -> Unit
            is StateFullResult.Success -> {
                setState {
                    copy(
                        homeScreenData = homeScreenData.copy(upComingMovies = incomingMovies.data.movies)
                    )
                }
            }
        }
        Timber.d("HAHA: stateData2: ${currentState.homeScreenData}")
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnSubmitFeedbackClick -> {

            }
        }
    }
}