package com.shalatan.entertainmentapp.ui.moviedetail

import android.R
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubePlayerView
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        val dataSource = MovieDatabase.getInstance(application).movieDAO

        val movie = DetailFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val detailViewModelFactory = DetailViewModelFactory(dataSource, movie, application)
        val detailViewModel =
            ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)

        binding.viewModel = detailViewModel
        binding.lifecycleOwner = this

        //movie poster view pager
        val moviePosterViewPager = binding.moviePosterViewPager
        moviePosterViewPager.clipToPadding = false
        val adapter = PostersAdapter(PostersAdapter.OnClickListener {
//            val extras = FragmentNavigatorExtras(binding.moviePosterViewPager to "poster_transition2")
            val directions = DetailFragmentDirections.actionDetailFragmentToPosterFragment(it)
            this.findNavController().navigate(directions)
        })
        moviePosterViewPager.adapter = adapter

        //if there's no backdrop images, remove the poster view pager else submit the data
        detailViewModel.completeMovieDetail.observe(viewLifecycleOwner, Observer {
            if (it.images?.backdrops.isNullOrEmpty()) {
                binding.moviePosterViewPager.visibility = View.GONE
            } else {
                adapter.submitList(it.images?.backdrops)
            }
        })

        //snackbar events
        detailViewModel.showAddedToWatchedSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    requireActivity().findViewById(R.id.content),
                    "ADDED TO WATCHED MOVIES",
                    Snackbar.LENGTH_SHORT
                ).show()
                makeButtonUiChanges(application, binding.addToWatched, binding.addToWatchLater)
                detailViewModel.doneShowingSnackbar()
            }
        })

        detailViewModel.showAddedToWatchLaterSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    requireActivity().findViewById(R.id.content),
                    "ADDED TO WATCH LATER MOVIES",
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                makeButtonUiChanges(application, binding.addToWatchLater, binding.addToWatched)
                detailViewModel.doneShowingSnackbar()
            }
        })

//        binding.movieTrailer.setOnClickListener{
//            youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY,
//                object : YouTubePlayer.OnInitializedListener {
//                    override fun onInitializationSuccess(
//                        provider: YouTubePlayer.Provider?,
//                        youTubePlayer: YouTubePlayer?,
//                        watched: Boolean
//                    ) {
//                        youTubePlayer?.loadVideo(detailViewModel.completeMovieDetail.value?.videos?.results?.get(0)?.key)
//                    }
//
//                    override fun onInitializationFailure(
//                        p0: YouTubePlayer.Provider?,
//                        p1: YouTubeInitializationResult?
//                    ) {
//                    }
//
//                })
//        }

        return binding.root
    }

    private fun makeButtonUiChanges(
        application: Application,
        imageButton1: ImageButton,
        imageButton2: ImageButton
    ) {
        imageButton1.isEnabled = false
        imageButton2.isEnabled = true
//        imageButton1.setBackgroundColor(application.getColor(R.color.holo_blue_dark))
//        imageButton2.setBackgroundColor(application.getColor(R.color.background_light))
    }
}