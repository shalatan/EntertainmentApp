package com.shalatan.entertainmentapp.ui.recommendation

import androidx.lifecycle.ViewModel
import com.shalatan.entertainmentapp.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {

    val recommendationMovies = repository.getAllRecommendedMovies()

}