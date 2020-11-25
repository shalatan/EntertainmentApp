package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.adapters.MovieAdapter
import com.shalatan.entertainmentapp.databinding.FragmentOverviewBinding
import com.shalatan.entertainmentapp.databinding.MovieItemBinding
import android.widget.Toast.makeText as makeText1


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

        binding.popularRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        binding.topRatedRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (null != it) {
//                val extras = FragmentNavigatorExtras(
//                    imageView to "main_transition")
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayMovieDetailsComplete()
            }
        })
        return binding.root
    }
}