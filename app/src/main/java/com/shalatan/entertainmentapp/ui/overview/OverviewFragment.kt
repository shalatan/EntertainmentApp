package com.shalatan.entertainmentapp.ui.overview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }
    private var searchBoxOpen = false
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Set up the tool bar
        (activity as AppCompatActivity).setSupportActionBar(binding.appBar)
        binding.appBar.setNavigationOnClickListener(
            NavigationIconClickListener(
                activity as AppCompatActivity,
                binding.movieScrollView,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_clapper_open),
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_clapper_close)
            )
        )

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

        binding.searchRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                val directions = OverviewFragmentDirections.actionShowDetail(it)
                this.findNavController().navigate(directions)
                viewModel.displayMovieDetailsComplete()
            }
        })

        binding.watchLaterMovies.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToWatchLaterMoviesFragment())
        }

        binding.watchedMovies.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToWatchedMoviesFragment())
        }

        binding.seriesSection.setOnClickListener {
            Snackbar.make(it, "Coming Soon !!", Snackbar.LENGTH_SHORT).show()
        }

        viewModel.openSearchBox.observe(viewLifecycleOwner, Observer {
            if (it) {
                closeTheSearchBox()
            } else {
                openTheSearchBox()
            }
        })
        return binding.root
    }

    private fun closeTheSearchBox() {
        binding.searchBarLinearLayout.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        searchBoxOpen = !searchBoxOpen
    }

    private fun openTheSearchBox() {
        binding.searchBarLinearLayout.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.VISIBLE
        searchBoxOpen = !searchBoxOpen
        binding.movieScrollView.post {
            binding.movieScrollView.fullScroll(View.FOCUS_DOWN)
        }
    }
}