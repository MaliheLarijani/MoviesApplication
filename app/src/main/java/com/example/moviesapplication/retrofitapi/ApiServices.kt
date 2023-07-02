package com.example.moviesapplication.retrofitapi


import com.example.moviesapplication.models.addmovie.AddMovieModel
import com.example.moviesapplication.models.genres.GenresData
import com.example.moviesapplication.models.login.LoginUserModel
import com.example.moviesapplication.models.moviesdetail.MoviesDetailModel
import com.example.moviesapplication.models.movieslist.Movies
import com.example.moviesapplication.models.registeruser.RegisterUserInput
import com.example.moviesapplication.models.registeruser.RegisterUserModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("api/v1/movies")
    fun getMovies(@Query("page") page: Int): Call<Movies>

    @GET("api/v1/movies")
    fun searchMovies(@Query("q") name: String, @Query("page") page: Int): Call<Movies>

    @GET("api/v1/movies/{movie_id}")
    fun getMoviesDetail(@Path("movie_id") id: Int): Call<MoviesDetailModel>

    @GET("api/v1/genres")
    fun getGenres(): Call<List<GenresData?>>

    @GET("api/v1/genres/{genre_id}/movies")
    fun getGenresMovies(@Path("genre_id") id: Int, @Query("page") page: Int): Call<Movies>

    @POST("api/v1/movies/multi")
    @Multipart
    fun addMovie(
        @Part("title") title: String,
        @Part("imdb_id") imdbId: String,
        @Part("country") country: String,
        @Part("year") year: Int,
        @Part("director") director: String,
        @Part("imdb_rating") imdbRating: String,
        @Part("imdb_votes") imdbVotes: String,
        @Part("poster") poster: RequestBody,
    ): Call<AddMovieModel>

    @POST("api/v1/register")
    fun registerUser(@Body registerUserInput: RegisterUserInput): Call<RegisterUserModel>


    @POST("oauth/token")
    fun loginUser(
        @Query("grant_type") grantType: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<LoginUserModel>

}