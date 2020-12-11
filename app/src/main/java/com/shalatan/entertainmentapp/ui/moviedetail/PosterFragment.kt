package com.shalatan.entertainmentapp.ui.moviedetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.FragmentPosterBinding

class PosterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentPosterBinding.inflate(inflater)
        val movie = PosterFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val posterViewModelFactory = PosterViewModelFactory(movie, application)
        val posterViewModel =
            ViewModelProvider(this, posterViewModelFactory).get(PosterViewModel::class.java)
        binding.posterViewModel = posterViewModel

        BigImageViewer.initialize(GlideImageLoader.with(application));

        val adapter = PostersAdapter()
        binding.moviePoster.adapter = adapter
        posterViewModel.images.observe(viewLifecycleOwner, Observer {
            Log.e("IMAGES VM - ", it.toString())
            adapter.submitList(it.backdrops)
        })
        return binding.root
    }
}