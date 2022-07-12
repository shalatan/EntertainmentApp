package com.shalatan.entertainmentapp.ui.recommendation

import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.ui.moviesection.SavedContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(private val repository: SavedContentRepository) :
    ViewModel() {

    val recommendationMovies = repository.getAllRecommendedMovies()
    // TODO: Implement the ViewModel

}