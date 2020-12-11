package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.onNavDestinationSelected
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.android.material.snackbar.Snackbar
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

        binding.posterMenu.setOnClickListener {
            val position = binding.moviePoster.currentItem
            showPosterPopUp(it, position)
        }
        return binding.root
    }

    fun showPosterPopUp(v: View, position: Int) {
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.poster_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.save -> {
                    Log.e("CLICKED", "SAVED")
                }
                R.id.wallpaper -> {
                    Log.e("CLICKED", "WALLPEPER")
                }
            }
            true
        }
        popup.show()
    }

}