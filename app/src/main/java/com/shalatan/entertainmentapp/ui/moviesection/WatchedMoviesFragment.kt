package com.shalatan.entertainmentapp.ui.moviesection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.databinding.FragmentWatchedMoviesBinding

class WatchedMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentWatchedMoviesBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val dataSource = MovieDatabase.getInstance(application).movieDAO
        val viewModelFactory = SavedContentViewModelFactory(dataSource, application)

        val savedContentViewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SavedContentViewModel::class.java)

        binding.viewModel = savedContentViewModel
        binding.lifecycleOwner = this

        binding.savedContentRecyclerView.adapter = SavedContentAdapter(SavedContentAdapter.OnClickListener {
            savedContentViewModel.displayMovieDetails(it)
        })

        savedContentViewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val directions = WatchLaterMoviesFragmentDirections.actionShowDetail(it)
                this.findNavController().navigate(directions)
                savedContentViewModel.displayMovieDetailsComplete()
            }
        })

        return binding.root
    }

}