package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    val viewModel: OverviewViewModel by viewModels()

    companion object {
        const val LOG = "app_log"
    }

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

        val nowPlayingMovieAdapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        val nowPlayingRecyclerView = binding.nowPlayingRecyclerView
        nowPlayingRecyclerView.adapter = nowPlayingMovieAdapter

        lifecycleScope.launch {
            viewModel.nowPlayingMoviesFlow.collect {
                if (it.isEmpty()) {

                } else {
                    nowPlayingMovieAdapter.submitList(it)
                    Timber.e("$LOG nowPlayingMovies: ${it.size}")
                    binding.nowPlayingRecyclerView.visibility = View.VISIBLE
                    binding.nowPlayingProgressBar.visibility = View.GONE
                }
            }
        }

        val popularMovieAdapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        val popularRecyclerView = binding.popularRecyclerView
        popularRecyclerView.adapter = popularMovieAdapter

        lifecycleScope.launch {
            viewModel.popularMoviesFlow.collect {
                if (it.isEmpty()) {

                } else {
                    popularMovieAdapter.submitList(it)
                    Timber.e("$LOG popularMovies: ${it.size}")
                    binding.popularRecyclerView.visibility = View.VISIBLE
                    binding.popularProgressBar.visibility = View.GONE
                }
            }
        }

        val upcomingMovieAdapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        val upcomingMovieRecyclerView = binding.upcomingRecyclerView
        upcomingMovieRecyclerView.adapter = upcomingMovieAdapter

        lifecycleScope.launch {
            viewModel.upcomingMoviesFlow.collect {
                if (it.isEmpty()) {

                } else {
                    upcomingMovieAdapter.submitList(it)
                    Timber.e("$LOG upcomingMovies: ${it.size}")
                    binding.upcomingRecyclerView.visibility = View.VISIBLE
                    binding.upcomingProgressBar.visibility = View.GONE
                }
            }
        }

        val topRatedMovieAdapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        val topRatedRecyclerView = binding.topRatedRecyclerView
        topRatedRecyclerView.adapter = topRatedMovieAdapter

        lifecycleScope.launch {
            viewModel.topRatedMoviesFlow.collect {
                if (it.isEmpty()) {

                } else {
                    topRatedMovieAdapter.submitList(it)
                    Timber.e("$LOG topRatedMovies: ${it.size}")
                    binding.topRatedRecyclerView.visibility = View.VISIBLE
                    binding.topRatedProgressBar.visibility = View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToSelectedMovie.collect {
                if (null != it) {
                    findNavController().navigate(NavGraphDirections.actionGlobalDetailFragment(it))
                    viewModel.displayMovieDetailsComplete()
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}