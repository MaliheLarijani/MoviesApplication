package com.example.moviesapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.models.moviesdetail.MoviesDetailModel
import com.example.moviesapplication.repositorys.MoviesRepository
import com.example.moviesapplication.retrofitapi.NetworkResponseCallback
import com.example.moviesapplication.utils.NetworkeHelper

class MoviesDetailViewModel(private val app: Application) : AndroidViewModel(app) {
    private lateinit var movies: MutableLiveData<MoviesDetailModel?>
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    var mRepository = MoviesRepository.getInstance()

    fun getMovie(forceFetch: Boolean,movieId:Int): MutableLiveData<MoviesDetailModel?> {
        if (NetworkeHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            movies = mRepository.getMoviesDetail(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, forceFetch,movieId)
        } else {
            mShowNetworkError.value = true
        }
        return movies
    }
}