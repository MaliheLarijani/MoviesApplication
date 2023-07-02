package com.example.moviesapplication.models.moviesdetail

data class MoviesDetailModel(
    var actors: String?,
    var awards: String?,
    var country: String?,
    var director: String?,
    var genres: List<String?>?,
    var id: Int?,
    var images: List<String?>?,
    var imdb_id: String?,
    var imdb_rating: String?,
    var imdb_votes: String?,
    var metascore: String?,
    var plot: String?,
    var poster: String?,
    var rated: String?,
    var released: String?,
    var runtime: String?,
    var title: String?,
    var type: String?,
    var writer: String?,
    var year: String?
)