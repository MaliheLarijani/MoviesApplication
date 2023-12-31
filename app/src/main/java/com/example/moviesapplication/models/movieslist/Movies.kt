package com.example.moviesapplication.models.movieslist

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("data")
    var data: List<MovieData>?,
    @SerializedName("metadata")
    var metadata: Metadata?
)