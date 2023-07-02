package com.example.moviesapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.models.addmovie.AddMovieModel
import com.example.moviesapplication.models.addmovie.MovieInput
import com.example.moviesapplication.repositorys.MoviesRepository
import com.example.moviesapplication.retrofitapi.NetworkResponseCallback
import com.example.moviesapplication.utils.NetworkeHelper

class AddMoviesViewModel (private val app: Application) : AndroidViewModel(app) {
    private lateinit var addMovie: MutableLiveData<AddMovieModel?>
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    var mRepository = MoviesRepository.getInstance()

    fun addMovie(forceFetch: Boolean, movieInput: MovieInput): MutableLiveData<AddMovieModel?> {
        if (NetworkeHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            addMovie = mRepository.addMovie(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, forceFetch, movieInput)
        } else {
            mShowNetworkError.value = true
        }
        return addMovie
    }


}