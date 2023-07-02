package com.example.moviesapplication.retrofitapi

interface NetworkResponseCallback {
    fun onResponseSuccess()
    fun onResponseFailure(th: Throwable)
}