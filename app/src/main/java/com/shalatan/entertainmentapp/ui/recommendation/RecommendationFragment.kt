package com.shalatan.entertainmentapp.ui.recommendation

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.databinding.FragmentRecommendationBinding
import com.shalatan.entertainmentapp.utils.Constants
import com.shalatan.entertainmentapp.utils.CustomViews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RecommendationFragment : Fragment() {

    val viewModel: RecommendationViewModel by viewModels()
    private lateinit var binding: FragmentRecommendationBinding
    private lateinit var recommendedMovies: List<SavedMovie>

    private var backgroundColor = 0
    private var textColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationBinding.inflate(inflater)

        val recommendationAdapter =
            RecommendationMovieAdapter(RecommendationMovieAdapter.OnClickListener {
                findNavController().navigate(NavGraphDirections.actionGlobalDetailFragment(it))
            })

        viewModel.recommendationMovies.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.savedContentText.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.savedContentText.visibility = View.GONE
                recommendedMovies = it
                recommendationAdapter.submitList(recommendedMovies)
            }
        }

        binding.recyclerView.apply {
            set3DItem(true)
            setAlpha(true)
            adapter = recommendationAdapter
            setItemSelectListener(object : CarouselLayoutManager.OnSelected {
                override fun onItemSelected(position: Int) {
                    val fullUrl = Constants.IMG_BASE_URL + recommendedMovies[position].moviePoster
                    Glide.with(requireActivity())
                        .asBitmap()
                        .load(fullUrl)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                lifecycleScope.launch {
                                    setUpBackgroundColor(resource)
                                }
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                // this is called when imageView is cleared on lifecycle call or for
                                // some other reason.
                                // if you are referencing the bitmap somewhere else too other than this imageView
                                // clear it here as you can no longer have the bitmap
                            }
                        })

                }
            })
        }

        return binding.root
    }

    private suspend fun setUpBackgroundColor(imgBitmap: Bitmap) {
        calculateDominantColor(imgBitmap)
        withContext(Dispatchers.Main) {
            binding.rootLayout.setBackgroundColor(backgroundColor)
            binding.rawRecommendedText.setTextColor(textColor)
            CustomViews().changeStatusBarColor(backgroundColor, requireActivity())
//            requireActivity().window.statusBarColor = dominantColor
//            bottomNavigationView.setBackgroundColor(dominantColor)
        }
    }

    private fun calculateDominantColor(
        bitmap: Bitmap
    ) = Palette.from(bitmap).generate().let { palette: Palette ->
        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                backgroundColor = palette.getDarkMutedColor(R.attr.colorSurface)
                textColor = palette.getLightVibrantColor(R.attr.colorOnSurface)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                backgroundColor = palette.getLightMutedColor(R.attr.colorSurface)
                textColor = palette.getDarkVibrantColor(R.attr.colorOnSurface)
            }
            else -> {
                backgroundColor = palette.getDarkMutedColor(R.attr.colorSurface)
                textColor = palette.getLightVibrantColor(R.attr.colorOnSurface)
            }
        }
    }
}