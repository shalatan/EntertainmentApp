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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val nowPlayingMovieAdapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        val nowPlayingRecyclerView = binding.nowPlayingRecyclerView
        nowPlayingRecyclerView.adapter = nowPlayingMovieAdapter

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowPlayingMoviesFlow.collect {
                Timber.d("$LOG collectedMovies1: $it")
                nowPlayingMovieAdapter.submitList(it)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getNowPlayingMovies().collect {
                    Timber.d("$LOG collectedMovies2: $it")
                    nowPlayingMovieAdapter.submitList(it.movies)
                }
            }
        }

//        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer {
//            if (it.isNullOrEmpty()) {
//
//            } else {
//                binding.nowPlayingRecyclerView.visibility = View.VISIBLE
//                binding.nowPlayingProgressBar.visibility = View.GONE
//            }
//        })
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