package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.nowPlayingRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        binding.popularRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        binding.topRatedRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        binding.upcomingRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                val directions = OverviewFragmentDirections.actionShowDetail(it)
                this.findNavController().navigate(directions)
                viewModel.displayMovieDetailsComplete()
            }
        })

        binding.seeAllNowPlaying.setOnClickListener {
            findNavController()
                .navigate(OverviewFragmentDirections.actionShowGrid(viewModel.nowPlayingMovies.value!!.toTypedArray()))
        }

        binding.seeAllPopular.setOnClickListener {
            findNavController()
                .navigate(OverviewFragmentDirections.actionShowGrid(viewModel.popularMovies.value!!.toTypedArray()))
        }

        binding.seeAllTopRated.setOnClickListener {
            findNavController()
                .navigate(OverviewFragmentDirections.actionShowGrid(viewModel.topRatedMovies.value!!.toTypedArray()))
        }

        binding.seeAllUpcoming.setOnClickListener {
            findNavController()
                .navigate(OverviewFragmentDirections.actionShowGrid(viewModel.upcomingMovies.value!!.toTypedArray()))
        }

        return binding.root
    }
}