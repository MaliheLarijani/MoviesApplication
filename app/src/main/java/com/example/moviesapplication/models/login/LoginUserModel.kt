package com.example.moviesapplication.models.login

import com.google.gson.annotations.SerializedName

data class LoginUserModel(
    @SerializedName("token_type")
    var access_token: String?,
    @SerializedName("expries_in")
    var expries_in: Int?,
    @SerializedName("refresh_token")
    var refresh_token: String?,
    @SerializedName("token_type")
    var token_type: String?
)