package com.shalatan.entertainmentapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.shalatan.entertainmentapp.presentation.viewmodels.HomeViewModel
import com.shalatan.entertainmentapp.utils.Constants

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        
    }
    val screenData = uiState.selectedMovieDetailScreenData
    Log.e(Constants.TAG, "MovieScreen: $screenData")
    Text(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        text = "${screenData.movie.title}",
        fontSize = 44.sp
    )
//    ConstraintLayout(modifier = modifier.fillMaxSize()) {
//        val (posterImage, scrollViewContent) = createRefs()
//
//        GlideImage(
//            modifier = Modifier.height(Dimensions.dimen_180),
//            model = "${ApiConstants.IMG_BASE_URL}${screenData.movie.posterPath}",
//            contentDescription = ""
//        )
////        AsyncImage(
////            model = ApiConstants.IMG_BASE_URL + screenData.movie.posterPath,
////            contentDescription = "movie poster",
////        )
//    }
}

//@Composable
//fun MovieDetailScreen() {
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        val (posterImage, scrollViewContent, startGuideline, endGuideline) = createRefs()
//
//        // Movie Poster
//        Image(
//            painter = painterResource(id = R.drawable.your_movie_poster),
//            contentDescription = stringResource(id = R.string.cd_movie_poster),
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(450.dp)
//                .constrainAs(posterImage) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//        )
//
//        // Scrollable content
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(scrollViewContent) {
//                    top.linkTo(posterImage.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .padding(16.dp)
//        ) {
//            // Where to Watch Text
//            Text(
//                text = stringResource(id = R.string.fragment_where_to_watch),
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // RecyclerView for movie genres (use LazyRow in Compose)
//            MovieGenreRow()
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Movie Title
//            Text(
//                text = "Movie Title",
//                style = MaterialTheme.typography.h6,
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            // Movie Rating
//            Text(
//                text = "4.5",
//                style = MaterialTheme.typography.body1
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Synopsis
//            Text(text = stringResource(id = R.string.synopsis))
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Movie Overview
//            Text(
//                text = "Lorem ipsum dolor sit amet...",
//                maxLines = 1
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Read More
//            Text(text = stringResource(id = R.string.read_more))
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Cast Section
//            Text(text = "Cast")
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Cast RecyclerView (use LazyRow in Compose)
//            CastRow()
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Poster Section
//            Text(text = "Posters")
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // ViewPager for Posters (use HorizontalPager from Accompanist)
//            PosterPager()
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Video Section
//            Text(text = "Videos")
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Video RecyclerView (use LazyRow in Compose)
//            VideoRow()
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Similar Movies Section
//            Text(text = "Similar Movies")
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Similar Movies RecyclerView (use LazyRow in Compose)
//            SimilarMoviesRow()
//        }
//    }
//}
