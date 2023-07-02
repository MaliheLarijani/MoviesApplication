package com.example.moviesapplication.models.movieslist

data class Metadata(
    var current_page: String?,
    var page_count: Int?,
    var per_page: Int?,
    var total_count: Int?
)