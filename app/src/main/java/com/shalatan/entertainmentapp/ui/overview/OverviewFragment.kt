package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    val viewModel: OverviewViewModel by viewModels()

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.searchFab.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalSearchFragment())
        }
        binding.watchedMoviesCard.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToWatchedMoviesFragment())
        }
        binding.watchLaterMoviesCard.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToWatchLaterMoviesFragment())
        }
        binding.recommendedMoviesCard.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToRecommendationFragment())
        }

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {

            } else {
                binding.nowPlayingRecyclerView.visibility = View.VISIBLE
                binding.nowPlayingProgressBar.visibility = View.GONE
            }
        })
        viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {

            } else {
                binding.topRatedRecyclerView.visibility = View.VISIBLE
                binding.topRatedProgressBar.visibility = View.GONE
            }
        })
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {

            } else {
                binding.popularRecyclerView.visibility = View.VISIBLE
                binding.popularProgressBar.visibility = View.GONE
            }
        })
        viewModel.upcomingMovies.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {

            } else {
                binding.upcomingRecyclerView.visibility = View.VISIBLE
                binding.upcomingProgressBar.visibility = View.GONE
            }
        })

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
                this.findNavController().navigate(NavGraphDirections.actionGlobalDetailFragment(it))
                viewModel.displayMovieDetailsComplete()
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}