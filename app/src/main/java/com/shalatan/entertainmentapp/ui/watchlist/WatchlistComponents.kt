package com.shalatan.entertainmentapp.ui.watchlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.utils.Constants
import com.shalatan.entertainmentapp.utils.getFullUrl

@Preview
@Composable
fun SavedItemPreview() {
    SavedItem(
        destination = Constants.DESTINATION_WATCHED,
        savedMovie = SavedMovie(
            1,
            "abc",
            "/lyQBXzOQSuE59IsHyhrp0qIiPAz.jpg",
            "abc",
        ),
        onClick = {

        }
    )
}

@Composable
fun SavedItem(
    destination: String,
    savedMovie: SavedMovie,
    modifier: Modifier = Modifier,
    onClick: (Movie) -> Unit
) {
    Card(
        shape = CardDefaults.outlinedShape,
        modifier = modifier.clickable {
            onClick(
                Movie(
                    title = savedMovie.movieTitle,
                    id = savedMovie.Id,
                    posterPath = savedMovie.moviePoster
                )
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .sizeIn(72.dp)
        ) {
            AsyncImage(
                model = getFullUrl(savedMovie.moviePoster),
                placeholder = painterResource(id = R.drawable.android_superhero2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = savedMovie.movieTitle!!,
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (savedMovie.isRated) {
                    Text(text = "Rating: ${savedMovie.rating}")
                } else {
                    Text(text = "Rec: ${savedMovie.recommendationWeight}")
                }
            }
        }
    }
}