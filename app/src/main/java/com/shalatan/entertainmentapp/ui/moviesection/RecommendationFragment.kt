package com.shalatan.entertainmentapp.ui.moviesection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shalatan.entertainmentapp.MainViewModel
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.databinding.FragmentDetailBinding
import com.shalatan.entertainmentapp.databinding.FragmentRecommendationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationFragment : Fragment() {

    val viewModel: SavedContentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding!!

    private lateinit var recommendedMovies: List<SavedMovie>

    private var backgroundColor = 0
    private var textColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendationBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerView.adapter = SavedContentAdapter(SavedContentAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController().navigate(NavGraphDirections.actionGlobalDetailFragment(it))
                viewModel.displayMovieDetailsCompleted()
            }
        }

        viewModel.recommendationMovies.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.savedContentText.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.savedContentText.visibility = View.GONE
                viewModel.updateHighestRecommendationWeight()
            }
        }

        binding.recInfo.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.fragment_rec_dialog_title))
                .setMessage(resources.getString(R.string.fragment_rec_dialog_content))
                .setNeutralButton(resources.getString(R.string.fragment_rec_dialog_refresh_button)) { dialog, which ->
                    mainViewModel.refreshRecommendations()
                }
                .setPositiveButton(resources.getString(R.string.fragment_rec_dialog_positive_button)) { dialog, which ->
                    dialog.cancel()
                }
                .show()
        }
//        viewModel.updateHighestRecommendationWeight()

//        binding.recyclerView.apply {
//            set3DItem(true)
//            setAlpha(true)
//            adapter = recommendationAdapter
//            setItemSelectListener(object : CarouselLayoutManager.OnSelected {
//                override fun onItemSelected(position: Int) {
//                    val fullUrl = Constants.IMG_BASE_URL + recommendedMovies[position].moviePoster
//                    Glide.with(requireActivity())
//                        .asBitmap()
//                        .load(fullUrl)
//                        .into(object : CustomTarget<Bitmap>() {
//                            override fun onResourceReady(
//                                resource: Bitmap,
//                                transition: Transition<in Bitmap>?
//                            ) {
//                                lifecycleScope.launch {
//                                    setUpBackgroundColor(resource)
//                                }
//                            }
//
//                            override fun onLoadCleared(placeholder: Drawable?) {
//                                // this is called when imageView is cleared on lifecycle call or for
//                                // some other reason.
//                                // if you are referencing the bitmap somewhere else too other than this imageView
//                                // clear it here as you can no longer have the bitmap
//                            }
//                        })
//
//                }
//            })
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private suspend fun setUpBackgroundColor(imgBitmap: Bitmap) {
//        calculateDominantColor(imgBitmap)
//        withContext(Dispatchers.Main) {
//            binding.rootLayout.setBackgroundColor(backgroundColor)
//            binding.rawRecommendedText.setTextColor(textColor)
//            CustomViews().changeStatusBarColor(backgroundColor, requireActivity())
////            requireActivity().window.statusBarColor = dominantColor
////            bottomNavigationView.setBackgroundColor(dominantColor)
//        }
//    }

//    private fun calculateDominantColor(
//        bitmap: Bitmap
//    ) = Palette.from(bitmap).generate().let { palette: Palette ->
//        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
//            Configuration.UI_MODE_NIGHT_YES -> {
//                backgroundColor = palette.getDarkMutedColor(R.attr.colorSurface)
//                textColor = palette.getLightVibrantColor(R.attr.colorOnSurface)
//            }
//            Configuration.UI_MODE_NIGHT_NO -> {
//                backgroundColor = palette.getLightMutedColor(R.attr.colorSurface)
//                textColor = palette.getDarkVibrantColor(R.attr.colorOnSurface)
//            }
//            else -> {
//                backgroundColor = palette.getDarkMutedColor(R.attr.colorSurface)
//                textColor = palette.getLightVibrantColor(R.attr.colorOnSurface)
//            }
//        }
//    }
}