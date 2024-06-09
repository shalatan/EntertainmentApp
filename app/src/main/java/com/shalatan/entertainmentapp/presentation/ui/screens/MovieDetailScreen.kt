package com.shalatan.entertainmentapp.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shalatan.entertainmentapp.presentation.contracts.MainContract.*
import com.shalatan.entertainmentapp.presentation.ui.NewMainViewModel
import com.shalatan.entertainmentapp.presentation.ui.theme.MoviesHexTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.model.Genre
import com.shalatan.entertainmentapp.presentation.ui.theme.Dimensions
import com.shalatan.entertainmentapp.presentation.ui.theme.LightBackgroundColor
import com.shalatan.entertainmentapp.utils.DummyData
import timber.log.Timber

@Preview(showSystemUi = true)
@Composable
private fun MovieDetailScreenPreview() {
    MoviesHexTheme {
        MovieDetailScreenContent(
            movieDetailScreenData = MovieDetailScreenData(
                completeMovieDetail = DummyData.completeMovieDetail,
                similarMovies = null
            )
        )
    }
}

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NewMainViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val movieDetailScreenData = uiState.movieDetailScreenData
    Timber.e("HAHA MDS: state: $movieDetailScreenData")
    MovieDetailScreenContent(movieDetailScreenData = movieDetailScreenData)
}

@Composable
fun MovieDetailScreenContent(
    modifier: Modifier = Modifier,
    movieDetailScreenData: MovieDetailScreenData
) {
    Timber.e("HAHA MDS: state: $movieDetailScreenData")
    val movieDetail = movieDetailScreenData.completeMovieDetail
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.blur(radius = Dimensions.dimen_32),
            painter = painterResource(id = R.drawable.android_superhero2),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Image Poster"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimensions.dimen_16)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(Dimensions.spacing_440))
            movieDetail?.genres?.let { GenresList(genres = it) }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimensions.dimen_12),
                fontSize = Dimensions.font_22,
                fontWeight = FontWeight.Bold,
                text = movieDetail?.title.toString()
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimensions.dimen_8),
                text = movieDetail?.popularity.toString(),
                fontSize = Dimensions.font_18
            )
        }
    }
}

@Composable
fun GenresList(
    modifier: Modifier = Modifier,
    genres: List<Genre>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.dimen_8)
    ) {
        items(genres) { genre ->
            Card(
                colors = CardDefaults.cardColors(containerColor = LightBackgroundColor),
                border = BorderStroke(width = Dimensions.dimen_2, color = Color.LightGray)
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = Dimensions.dimen_8,
                        vertical = Dimensions.dimen_2
                    ),
                    text = genre.name,
                    fontSize = Dimensions.font_14
                )
            }
        }
    }
}

@Composable
fun MovieDetailsScreen(
    movieTitle: String,
    movieRating: String,
    synopsis: String,
    overview: String,
    cast: List<String>, // Dummy data type for cast list
    posters: List<Int>, // Dummy data type for poster images
    videos: List<String>, // Dummy data type for video list
    similarMovies: List<String> // Dummy data type for similar movies
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//        val (invisible, whereToWatchText, movieGenreRecyclerView, movieName, movieRatingView,
//            rawSynopsis, movieOverview, rawSynopsisReadMore, watchLaterLayout, ratedLayout,
//            rawCastSection, movieCastRecyclerView, rawPosterSection, moviePosterViewPager,
//            rawVideoSection, movieVideoRecyclerView, movieSimilarTextView,
//            movieSimilarRecyclerView, startGuideline, endGuideline) = createRefs()
//            createRef()

        // Invisible view
//        Box(modifier = Modifier
//            .height(450.dp)
//            .fillMaxWidth()
//            .constrainAs(invisible) {
//                top.linkTo(parent.top)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//        // Where to watch text
//        Text(
//            text = "Where to Watch", // Replace with string resource
//            fontSize = 14.sp, // Replace with your desired text size
//            modifier = Modifier
//                .padding(bottom = 16.dp)
//                .constrainAs(whereToWatchText) {
//                    bottom.linkTo(movieGenreRecyclerView.top)
//                    end.linkTo(endGuideline)
//                }
//        )
//
//        // Movie genre RecyclerView replacement
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(movieGenreRecyclerView) {
//                    top.linkTo(invisible.bottom)
//                }
//        ) {
//            items(listOf("Genre1", "Genre2")) { genre ->
//                Text(text = genre) // Replace with your genre item layout
//            }
//        }
//
//        // Movie name
//        Text(
//            text = movieTitle,
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(top = 12.dp)
//                .constrainAs(movieName) {
//                    top.linkTo(movieGenreRecyclerView.bottom)
//                    start.linkTo(startGuideline)
//                    end.linkTo(endGuideline)
//                }
//        )
//
//        // Movie rating
//        Text(
//            text = movieRating,
//            fontSize = 16.sp,
//            modifier = Modifier
//                .padding(top = 4.dp)
//                .constrainAs(movieRatingView) {
//                    top.linkTo(movieName.bottom)
//                    start.linkTo(startGuideline)
//                }
//        )
//
//        // Synopsis
//        Text(
//            text = "Synopsis",
//            fontSize = 16.sp,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .constrainAs(rawSynopsis) {
//                    top.linkTo(movieRatingView.bottom)
//                    start.linkTo(startGuideline)
//                }
//        )
//
//        // Movie overview
//        Text(
//            text = overview,
//            maxLines = 1,
//            fontSize = 14.sp,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .constrainAs(movieOverview) {
//                    top.linkTo(rawSynopsis.bottom)
//                    start.linkTo(startGuideline)
//                    end.linkTo(endGuideline)
//                }
//        )
//
//        // Read more
//        Text(
//            text = "Read More",
//            fontSize = 14.sp,
//            modifier = Modifier
//                .padding(top = 8.dp)
//                .constrainAs(rawSynopsisReadMore) {
//                    top.linkTo(movieOverview.bottom)
//                    end.linkTo(ratedLayout.end)
//                }
//        )
//
//        // Watch later layout
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .padding(end = 24.dp)
//                .constrainAs(watchLaterLayout) {
//                    bottom.linkTo(ratedLayout.bottom)
//                    end.linkTo(ratedLayout.start)
//                }
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_watch_later_false),
//                contentDescription = null
//            )
//            Text(
//                text = "Watch List",
//                fontSize = 14.sp,
//                modifier = Modifier.padding(top = 4.dp)
//            )
//        }
//
//        // Rated layout
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .padding(end = 24.dp)
//                .constrainAs(ratedLayout) {
//                    bottom.linkTo(rawSynopsis.bottom)
//                    end.linkTo(endGuideline)
//                }
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_watched_false),
//                contentDescription = null
//            )
//            Text(
//                text = "Rate",
//                fontSize = 14.sp,
//                modifier = Modifier.padding(top = 4.dp)
//            )
//        }
//
//        // Cast section
//        Text(
//            text = "Cast",
//            fontSize = 16.sp,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .constrainAs(rawCastSection) {
//                    top.linkTo(movieOverview.bottom)
//                    start.linkTo(startGuideline)
//                }
//        )
//
//        // Cast RecyclerView replacement
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 12.dp)
//                .constrainAs(movieCastRecyclerView) {
//                    top.linkTo(rawCastSection.bottom)
//                }
//        ) {
//            items(cast) { castMember ->
//                Text(text = castMember) // Replace with your cast item layout
//            }
//        }
//
//        // Poster section
//        Text(
//            text = "Posters",
//            fontSize = 16.sp,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .constrainAs(rawPosterSection) {
//                    top.linkTo(movieCastRecyclerView.bottom)
//                    start.linkTo(startGuideline)
//                }
//        )
//
//        // Poster ViewPager2 replacement
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(260.dp)
//                .padding(top = 12.dp, bottom = 40.dp)
//                .constrainAs(moviePosterViewPager) {
//                    top.linkTo(rawPosterSection.bottom)
//                }
//        ) {
//            items(posters) { poster ->
//                Image(
//                    painter = painterResource(id = poster),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .width(200.dp)
//                        .height(260.dp)
//                )
//            }
//        }
//
//        // Video section
//        Text(
//            text = "Videos",
//            fontSize = 16.sp,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .constrainAs(rawVideoSection) {
//                    top.linkTo(moviePosterViewPager.bottom)
//                    start.linkTo(startGuideline)
//                }
//        )
//
//        // Video RecyclerView replacement
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp)
//                .constrainAs(movieVideoRecyclerView) {
//                    top.linkTo(rawVideoSection.bottom)
//                }
//        ) {
//            items(videos) { video ->
//                Text(text = video) // Replace with your video item layout
//            }
//        }
//
//        // Similar movies section
//        Text(
//            text = "Similar Movies",
//            fontSize = 16.sp,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .constrainAs(movieSimilarTextView) {
//                    top.linkTo(movieVideoRecyclerView.bottom)
//                    start.linkTo(startGuideline)
//                }
//        )
//
//        // Similar movies RecyclerView replacement
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp)
//                .constrainAs(movieSimilarRecyclerView) {
//                    top.linkTo(movieSimilarTextView.bottom)
//                }
//        ) {
//            items(similarMovies) { similarMovie ->
//                Text(text = similarMovie) // Replace with your similar movie item layout
//            }
//        }
    }
}