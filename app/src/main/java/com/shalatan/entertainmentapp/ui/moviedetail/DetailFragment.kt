package com.shalatan.entertainmentapp.ui.moviedetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.databinding.FragmentDetailBinding
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.ui.overview.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    val viewModel: DetailViewModel by viewModels()
    lateinit var movie: Movie
    lateinit var binding: FragmentDetailBinding
    lateinit var movieWatchedIcon: ImageView
    lateinit var movieWatchLaterIcon: ImageView
    private var isRated = false
    private var isWatchLater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = DetailFragmentArgs.fromBundle(requireArguments()).selectedMovie
        viewModel.fetchMovieData(movie)
        viewModel.isMovieSavedInWatchList(movie.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.explode)

        binding = FragmentDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        movieWatchedIcon = binding.ratedLayoutImage
        movieWatchLaterIcon = binding.watchLaterLayoutImage

        //movie poster view pager
        val postersAdapter = PostersAdapter(PostersAdapter.OnClickListener {
            val directions = DetailFragmentDirections.actionDetailFragmentToPosterFragment(it)
            this.findNavController().navigate(directions)
        })
        val moviePosterViewPager = binding.moviePosterViewPager
        moviePosterViewPager.adapter = postersAdapter
        setUpPosterViewPager(moviePosterViewPager)

        val movieCastRecyclerView = binding.movieCastRecyclerView
        val movieCastAdapter = MovieCastAdapter()
        movieCastRecyclerView.adapter = movieCastAdapter

        val genreAdapter = GenreAdapter()
        binding.movieGenreRecyclerView.adapter = genreAdapter

        //if there's no backdrop images, remove the poster view pager else submit the data
        viewModel.completeMovieDetail.observe(viewLifecycleOwner) {
            binding.movieOverview.text = it.overview
            if (it.images?.backdrops.isNullOrEmpty()) {
                binding.moviePosterViewPager.visibility = View.GONE
            } else {
                postersAdapter.submitList(it.images?.backdrops)
            }
        }

        binding.watchLaterLayout.setOnClickListener {
            if (isWatchLater) {
                if (isRated) {
                    viewModel.updateExistingMovieData(isWatchLater = false, isRated = true)
                } else {
                    viewModel.updateExistingMovieData(isWatchLater = false, isRated = false)
                }
                markMovieAsWatchLaterFalse()
            } else {
                //if it's not in watch later list, drop logic further down
                if (isRated) {
                    //if it's already rated, means it exists in db, hence update
                    viewModel.updateExistingMovieData(isWatchLater = true, isRated = true)
                } else {
                    //if it's not rated also, means it doesn't exist in db, hence insert
                    viewModel.addMovieToWatchList(isRated = false, isWatchLater = true)
                }
                markMovieAsWatchLaterTrue()
            }
        }

        binding.ratedLayout.setOnClickListener {
            if (isRated) {
                if (isWatchLater) {
                    viewModel.updateExistingMovieData(isWatchLater = true, isRated = false)
                } else {
                    viewModel.updateExistingMovieData(isWatchLater = false, isRated = false)
                }
                markMovieAsRatedFalse()
            } else {
                if (isWatchLater) {
                    viewModel.updateExistingMovieData(isWatchLater = true, isRated = true)
                } else {
                    viewModel.addMovieToWatchList(isRated = true, isWatchLater = false)
                }
                markMovieAsRatedTrue()
            }
        }

        viewModel.isMovieExistInWatchedList.observe(viewLifecycleOwner) {
            if (it) {
                markMovieAsRatedTrue()
            } else {
                markMovieAsRatedFalse()
            }
        }

        viewModel.isMovieExistInWatchLaterList.observe(viewLifecycleOwner) {
            if (it) {
                markMovieAsWatchLaterTrue()
            } else {
                markMovieAsWatchLaterFalse()
            }
        }

        binding.movieSimilarRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalDetailFragment(it))
        })

        return binding.root
    }

    private fun markMovieAsWatchLaterTrue() {
        movieWatchLaterIcon.setImageResource(com.shalatan.entertainmentapp.R.drawable.ic_watch_later_true)
        isWatchLater = true
    }

    private fun markMovieAsWatchLaterFalse() {
        isWatchLater = false
        movieWatchLaterIcon.setImageResource(com.shalatan.entertainmentapp.R.drawable.ic_watch_later_false)
    }

    private fun markMovieAsRatedTrue() {
        isRated = true
        movieWatchedIcon.setImageResource(com.shalatan.entertainmentapp.R.drawable.ic_watched_true)
    }

    private fun markMovieAsRatedFalse() {
        isRated = false
        movieWatchedIcon.setImageResource(com.shalatan.entertainmentapp.R.drawable.ic_watched_false)
    }

    /**
     * function to make view pager view multiple items
     */
    private fun setUpPosterViewPager(moviePosterViewPager: ViewPager2) {
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
    }
}