package com.shalatan.entertainmentapp.ui.moviedetail.poster

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.android.material.snackbar.Snackbar
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.FragmentPosterBinding

class PosterFragment : Fragment() {

    lateinit var posterViewModel: PosterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        BigImageViewer.initialize(GlideImageLoader.with(application))

        val binding = FragmentPosterBinding.inflate(inflater)

        val posterURL = PosterFragmentArgs.fromBundle(requireArguments()).posterURL
        Log.e("RECV URL", posterURL)
        val posterViewModelFactory = PosterViewModelFactory(posterURL, application)
        posterViewModel =
            ViewModelProvider(this, posterViewModelFactory).get(PosterViewModel::class.java)
        binding.posterViewModel = posterViewModel

        binding.posterMenu.setOnClickListener {
            showPosterPopUp(it, posterURL)
        }

        //show snackbar when wallpaper is set successfully
        posterViewModel.posterSetAsWallpaperSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "ENJOY YOUR NEW WALLPAPER ;)",
                    Snackbar.LENGTH_SHORT
                ).show()
                posterViewModel.doneShowingSnackbar()
            }
        })
        return binding.root
    }

    /**
     * show pop up menu
     */
    private fun showPosterPopUp(v: View, poster: String?) {
        val popup = PopupMenu(requireActivity(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.poster_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.save -> {
                    Snackbar.make(v.rootView, "Coming Soon !!", Snackbar.LENGTH_SHORT).show()
                }
                R.id.wallpaper -> {
                    posterViewModel.setImageAsWallpaper(poster)
                }
            }
            true
        }
        popup.show()
    }

}