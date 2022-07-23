package com.shalatan.entertainmentapp.ui.moviedetail.poster

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.FragmentPosterBinding
import com.shalatan.entertainmentapp.databinding.FragmentSearchBinding

class PosterFragment : Fragment() {

    private lateinit var posterViewModel: PosterViewModel

    private var _binding: FragmentPosterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(activity).application

        _binding = FragmentPosterBinding.inflate(inflater)


        val posterURL = PosterFragmentArgs.fromBundle(requireArguments()).posterURL
        val posterViewModelFactory = PosterViewModelFactory(posterURL, application)
        posterViewModel =
            ViewModelProvider(this, posterViewModelFactory)[PosterViewModel::class.java]
        binding.posterViewModel = posterViewModel

        binding.posterMenu.setOnClickListener {
            showPosterPopUp(it, posterURL)
        }

        //show snackbar when wallpaper is set successfully
        posterViewModel.posterSetAsWallpaperSnackbarEvent.observe(viewLifecycleOwner) {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "ENJOY YOUR NEW WALLPAPER ;)",
                    Snackbar.LENGTH_SHORT
                ).show()
                posterViewModel.doneShowingSnackbar()
            }
        }
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
                R.id.wallpaper -> {
                    Snackbar.make(v.rootView, "Please Wait !!", Snackbar.LENGTH_SHORT).show()
                    posterViewModel.setImageAsWallpaper(poster)
                }
            }
            true
        }
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}