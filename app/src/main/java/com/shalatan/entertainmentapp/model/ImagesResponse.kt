package com.shalatan.entertainmentapp.model

data class ImagesResponse(
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<Poster>
)