package com.shalatan.entertainmentapp.utils

import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.Genre

object DummyData {

    val completeMovieDetail = CompleteMovieDetail(
        adult = true,
        backdrop_path = "/TU9NIjwzjoKPwQHoHshkFcQUCG.jpg",
        title = "Parasite",
        genres = listOf<Genre>(Genre(id = 28, name = "Action"), Genre(id = 12, name = "Adventure")),
        overview = "All unemployed, Ki-taek's family takes peculiar interest in the wealthy and glamorous Parks for their livelihood until they get entangled in an unexpected incident.",
        poster_path = "/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
        release_date = "2019-05-30",
        runtime = 132,
        original_title = "Parasite",
        popularity = 6.9
    )
}