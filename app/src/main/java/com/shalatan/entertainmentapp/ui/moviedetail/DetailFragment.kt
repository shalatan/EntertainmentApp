package com.shalatan.entertainmentapp.ui.moviedetail

import android.R
import android.app.Application
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubePlayerView
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.databinding.FragmentDetailBinding
import java.lang.Math.abs
import java.time.Clock.offset


class DetailFragment : Fragment() {

    lateinit var youTubePlayerView: YouTubePlayerView
//    @JvmStatic
//    fun adjustViewPager(context: Context, viewPager2: ViewPager2) {
//        val nextItemVisiblePx = context.resources.getDimension(R.dimen.viewpager_next_item_visible)
//        val currentItemHorizontalMarginPx =
//            context.resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
//        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
//        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
//            page.translationX = -pageTranslationX * position
//            // Next line scales the item's height. You can remove it if you don't want this effect
//            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
//            // If you want a fading effect uncomment the next line:
//            // page.alpha = 0.25f + (1 - abs(position))
//        }
//        viewPager2.setPageTransformer(pageTransformer)
//        val itemDecoration = HorizontalMarginItemDecoration(
//            context,
//            R.dimen.viewpager_current_item_horizontal_margin
//        )
//        viewPager2.addItemDecoration(itemDecoration)
//    }

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
        val adapter = PostersAdapter(PostersAdapter.OnClickListener {
            val directions = DetailFragmentDirections.actionDetailFragmentToPosterFragment(it)
            this.findNavController().navigate(directions)
        })
        val moviePosterViewPager = binding.moviePosterViewPager
        with(moviePosterViewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        val pageMarginPx = 20 * resources.displayMetrics.density
        val offsetPx = 30 * resources.displayMetrics.density
        moviePosterViewPager.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }

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
        imageButton1: ImageView,
        imageButton2: ImageView
    ) {
//        imageButton1.isEnabled = false
//        imageButton2.isEnabled = true
        imageButton1.isPressed = true
        imageButton2.isPressed = false
//        imageButton1.setBackgroundColor(application.getColor(R.color.holo_blue_dark))
//        imageButton2.setBackgroundColor(application.getColor(R.color.background_light))
    }

}