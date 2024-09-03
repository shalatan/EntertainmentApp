package com.shalatan.entertainmentapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.shalatan.entertainmentapp.data.remote.ApiConstants
import com.shalatan.entertainmentapp.domain.model.Movie
import com.shalatan.entertainmentapp.presentation.ui.theme.Dimensions
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
        ) {

        }
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
                bottom = Dimensions.dimen_8
            )
        )
        content()
    }
}

@Composable
fun MovieListHorizontal(
    modifier: Modifier = Modifier,
    moviesList: List<Movie>?,
    onItemClick: (Int) -> Unit
) {
    if (!moviesList.isNullOrEmpty()) {
        LazyRow(modifier = modifier) {
            items(moviesList) { movie ->
                MovieItem(movie = movie, onItemClick = {
                    onItemClick.invoke(it)
                })
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onItemClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onItemClick.invoke(movie.id) }
            .padding(4.dp)
            .width(120.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier.height(Dimensions.dimen_180),
            model = "${ApiConstants.IMG_BASE_URL}${movie.posterPath}",
            contentDescription = ""
        )
        Text(
            textAlign = TextAlign.Center,
            text = movie.title.toString(),
            fontSize = Dimensions.font_14,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 2.dp),
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}