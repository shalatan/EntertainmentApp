package com.shalatan.entertainmentapp.ui.moviedetail

import android.R.attr.name
import android.os.Bundle
import android.text.Html
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.MainViewModel
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.databinding.FragmentDetailBinding
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.ui.overview.MovieAdapter
import com.shalatan.entertainmentapp.utils.CustomViews
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    val viewModel: DetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    lateinit var movie: Movie
    lateinit var binding: FragmentDetailBinding
    private lateinit var movieWatchedIcon: ImageView
    private lateinit var movieWatchLaterIcon: ImageView
    private lateinit var movieOverviewReadMoreTextView: TextView
    private lateinit var movieOverviewTextView:TextView
    private var isRated = false
    private var isWatchLater = false

    val stringReadLess = "<b><u>Read Less</u></b>"
    val stringReadMore = "<b><u>Read Less</u></b>"

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
        movieOverviewReadMoreTextView = binding.rawSynopsisReadMore
        movieOverviewTextView = binding.movieOverview

        //movie overview
        var isTextViewFull = false
        movieOverviewReadMoreTextView.setOnClickListener {
            if (isTextViewFull) {
                movieOverviewTextView.maxLines = 1
                movieOverviewReadMoreTextView.text = Html.fromHtml(stringReadMore)
            } else {
                movieOverviewTextView.maxLines = Integer.MAX_VALUE
                movieOverviewReadMoreTextView.text = Html.fromHtml(stringReadLess)            }
            isTextViewFull = !isTextViewFull
        }

        //movie poster
        val postersAdapter = PostersAdapter(PostersAdapter.OnClickListener {
            val directions = DetailFragmentDirections.actionDetailFragmentToPosterFragment(it)
            this.findNavController().navigate(directions)
        })
        val moviePosterViewPager = binding.moviePosterViewPager
        moviePosterViewPager.adapter = postersAdapter
        CustomViews().setUpPosterViewPager(moviePosterViewPager, requireActivity())

        //movie cast
        val movieCastRecyclerView = binding.movieCastRecyclerView
        val movieCastAdapter = MovieCastAdapter()
        movieCastRecyclerView.adapter = movieCastAdapter

        //movie genre
        val genreAdapter = GenreAdapter()
        binding.movieGenreRecyclerView.adapter = genreAdapter

        //if there's no backdrop images, remove the poster view pager else submit the data
        viewModel.completeMovieDetail.observe(viewLifecycleOwner) {
            if (it.images?.backdrops.isNullOrEmpty()) {
                binding.moviePosterViewPager.visibility = View.GONE
            } else {
                postersAdapter.submitList(it.images?.backdrops)
            }
        }

        binding.watchLaterLayout.setOnClickListener {
            if (isWatchLater) {
                viewModel.updateWatchLaterStatus(!isWatchLater)
                markMovieAsWatchLaterFalse()
            } else {
                viewModel.addMovieToWatchList(isRated = false, isWatchLater = true)
                viewModel.updateWatchLaterStatus(!isWatchLater)
                markMovieAsWatchLaterTrue()
            }
        }

        var recommendedMovies = emptyList<Movie>()
        viewModel.recommendedMovies.observe(viewLifecycleOwner) {
            recommendedMovies = it
        }

        val ratingBar = binding.movieRatingBar
        binding.ratedLayout.setOnClickListener {
            if (isRated) {
                viewModel.updateRatedStatus(isRated = false, rating = 0f)
                markMovieAsRatedFalse()
            } else {
                ratingBar.visibility = View.VISIBLE
                ratingBar.onRatingBarChangeListener =
                    OnRatingBarChangeListener { rb, rating, fromUser ->
                        if (fromUser) {
                            viewModel.addMovieToWatchList(isWatchLater = false, isRated = true)
                            viewModel.updateRatedStatus(isRated = true, rating = rating)
                            mainViewModel.recommendMovie(movie.id, recommendedMovies, rating)
                            rb.visibility = View.INVISIBLE
                        }
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
}