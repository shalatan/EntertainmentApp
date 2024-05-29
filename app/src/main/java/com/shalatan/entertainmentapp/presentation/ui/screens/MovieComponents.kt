package com.shalatan.entertainmentapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.presentation.ui.theme.Dimensions
import com.shalatan.entertainmentapp.utils.Constants
import java.util.Locale

@Preview(showSystemUi = true)
@Composable
private fun CommonComponentsPreview() {
    MovieSlot(title = "Trending Movies") {
        MovieListHorizontal(
            moviesList = listOf(
                Movie(
                    posterPath = "/z1p34vh7dEOnLDmyCrlUVLuoDzd.jpg",
                    title = "Phir hera pheri"
                ), Movie(
                    title = "Phir hera pheri Phir hera Pheri \n Phir Hera"
                ), Movie(
                    title = "Phir hera pheri \n Phir Hera"
                ), Movie(
                    title = "Phir hera pheri"
                )
            )
        )
    }
}

@Composable
fun MovieSlot(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = Dimensions.font_22,
            modifier = Modifier.padding(
                start = Dimensions.dimen_16,
                bottom = Dimensions.dimen_16
            )
        )
        content()
    }
}

@Composable
fun MovieListHorizontal(modifier: Modifier = Modifier, moviesList: List<Movie>?) {
    if (!moviesList.isNullOrEmpty()) {
        LazyRow(modifier = modifier) {
            items(moviesList) { movie ->
                MovieItem(movie = movie)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(modifier: Modifier = Modifier, movie: Movie) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .width(100.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier.height(height = 140.dp),
            model = "${Constants.IMG_BASE_URL}${movie.posterPath}",
            contentDescription = ""
        )
        Text(
            text = movie.title.toString(),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 2.dp),
            minLines = 2,
            maxLines = 2
        )
    }
}