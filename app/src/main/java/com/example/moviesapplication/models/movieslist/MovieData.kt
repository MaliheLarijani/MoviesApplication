package com.example.moviesapplication.models.movieslist

data class MovieData(
    var country: String?,
    var genres: List<String?>?,
    var id: Int?,
    var images: List<String?>?,
    var imdb_rating: String?,
    var poster: String?,
    var title: String?,
    var year: String?
)