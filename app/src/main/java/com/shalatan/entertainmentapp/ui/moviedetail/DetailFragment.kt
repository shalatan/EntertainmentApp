package com.shalatan.entertainmentapp.ui.moviedetail

import android.R
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.databinding.FragmentDetailBinding
import com.shalatan.entertainmentapp.model.Movie


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        sharedElementEnterTransition = MaterialContainerTransform().apply {
//            // Scope the transition to a view in the hierarchy so we know it will be added under
//            // the bottom app bar but over the elevation scale of the exiting HomeFragment.
//            duration = 300.toLong()
//            scrimColor = Color.TRANSPARENT
//        }

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        val dataSource = MovieDatabase.getInstance(application).movieDAO

        val movie = DetailFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val detailViewModelFactory = DetailViewModelFactory(dataSource, movie, application)
        val detailViewModel =
            ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)

        ViewCompat.setTransitionName(binding.moviePoster, movie.id.toString())

        binding.viewModel = detailViewModel
        binding.lifecycleOwner = this

        detailViewModel.showAddedToWatchedSnackbarEvent.observe(viewLifecycleOwner, Observer {
            Log.d("ENTERED VIEW MODEL", it.toString())
            if (it == true) { // Observed state is true.
                Log.d("ENTERED VIEW MODEL", it.toString())
                Snackbar.make(
                    requireActivity().findViewById(R.id.content),
                    "ADDED TO WATCHED MOVIES",
                    Snackbar.LENGTH_SHORT
                ).show()
                makeButtonUiChanges(application, binding.addToWatched, binding.addToWatchLater)
                binding.addToWatched.setBackgroundColor(application.getColor(R.color.holo_red_dark))
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
        return binding.root
    }

    private fun makeButtonUiChanges(
        application: Application,
        imageButton1: ImageButton,
        imageButton2: ImageButton
    ) {
        imageButton1.isEnabled = false
        imageButton2.isEnabled = true
        imageButton1.setBackgroundColor(application.getColor(R.color.holo_blue_dark))
        imageButton2.setBackgroundColor(application.getColor(R.color.background_light))
    }

//    override fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }
}