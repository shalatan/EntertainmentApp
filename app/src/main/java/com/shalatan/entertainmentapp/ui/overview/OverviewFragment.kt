package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import android.widget.Toast.makeText
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Set up the tool bar
        (activity as AppCompatActivity).setSupportActionBar(binding.appBar)
        binding.appBar.setNavigationOnClickListener(
            NavigationIconClickListener(
                activity as AppCompatActivity,
                binding.movieScrollView,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_branded_menu),
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_close_menu)
            )
        )

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

        binding.watchLaterMovies.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToWatchLaterMoviesFragment())
        }

        binding.watchedMovies.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToWatchedMoviesFragment())
        }

        binding.seriesSection.setOnClickListener {
            Snackbar.make(it, "Coming Soon !!", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}