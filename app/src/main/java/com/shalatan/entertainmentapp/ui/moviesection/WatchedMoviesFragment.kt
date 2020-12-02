package com.shalatan.entertainmentapp.ui.moviesection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.databinding.FragmentWatchedMoviesBinding

class WatchedMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWatchedMoviesBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataSource = MovieDatabase.getInstance(application).movieDAO

        val viewModelFactory = WatchedMoviesViewModelFactory(dataSource, application)

        val watchedMoviesViewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(WatchedMoviesViewModel::class.java)

        binding.viewModel = watchedMoviesViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

}