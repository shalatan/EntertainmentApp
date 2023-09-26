package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.databinding.FragmentOverviewBinding
import com.shalatan.entertainmentapp.network.Response
import com.shalatan.entertainmentapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()

    companion object {
        const val LOG = "app_log"
    }

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOverviewBinding.inflate(inflater)

        binding.searchFab.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalSearchFragment())
        }
        binding.watchedMoviesCard.setOnClickListener {
            findNavController().navigate(
                OverviewFragmentDirections.actionOverviewFragmentToWatchlistFragment(
                    Constants.DESTINATION_WATCHED
                )
            )
        }
        binding.watchLaterMoviesCard.setOnClickListener {
            findNavController().navigate(
                OverviewFragmentDirections.actionOverviewFragmentToWatchlistFragment(
                    Constants.DESTINATION_WATCH_LATER
                )
            )
        }
        binding.recommendedMoviesCard.setOnClickListener {
            findNavController().navigate(
                OverviewFragmentDirections.actionOverviewFragmentToWatchlistFragment(
                    Constants.DESTINATION_RECOMMENDATION
                )
            )
        }

        val nowPlayingMovieAdapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        val nowPlayingRecyclerView = binding.nowPlayingRecyclerView
        nowPlayingRecyclerView.adapter = nowPlayingMovieAdapter

        lifecycleScope.launch {
            viewModel.nowPlayingMoviesFlow.collect {
                if (it is Response.Success) {
                    if (it.data!!.isNotEmpty()) {
                        nowPlayingMovieAdapter.submitList(it.data)
                        binding.nowPlayingRecyclerView.visibility = View.VISIBLE
                        binding.nowPlayingProgressBar.visibility = View.GONE
                    }
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
                if (it is Response.Success) {
                    if (it.data!!.isNotEmpty()) {
                        popularMovieAdapter.submitList(it.data)
                        binding.popularRecyclerView.visibility = View.VISIBLE
                        binding.popularProgressBar.visibility = View.GONE
                    }
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
                if (it is Response.Success) {
                    if (it.data!!.isNotEmpty()) {
                        upcomingMovieAdapter.submitList(it.data)
                        binding.upcomingRecyclerView.visibility = View.VISIBLE
                        binding.upcomingProgressBar.visibility = View.GONE
                    }
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
                if (it is Response.Success) {
                    if (it.data!!.isNotEmpty()) {
                        topRatedMovieAdapter.submitList(it.data)
                        binding.topRatedRecyclerView.visibility = View.VISIBLE
                        binding.topRatedProgressBar.visibility = View.GONE
                    }
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