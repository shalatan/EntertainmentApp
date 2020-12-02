package com.shalatan.entertainmentapp.ui.moviedetail

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

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

        detailViewModel.showAddedToWatchedSnackbarEvent.observe(viewLifecycleOwner, Observer {
            Log.d("ENTERED VIEW MODEL", it.toString())
            if (it == true) { // Observed state is true.
                Log.d("ENTERED VIEW MODEL", it.toString())
                Snackbar.make(
                    requireActivity().findViewById(R.id.content),
                    "ADDED TO WATCHED MOVIES",
                    Snackbar.LENGTH_SHORT
                ).show()
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
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
                detailViewModel.doneShowingSnackbar()
            }
        })
        return binding.root
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