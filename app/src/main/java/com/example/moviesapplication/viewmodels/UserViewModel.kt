package com.example.moviesapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.models.login.LoginUserModel
import com.example.moviesapplication.models.registeruser.RegisterUserInput
import com.example.moviesapplication.models.registeruser.RegisterUserModel
import com.example.moviesapplication.repositorys.UserRepository
import com.example.moviesapplication.retrofitapi.NetworkResponseCallback
import com.example.moviesapplication.utils.NetworkeHelper


class UserViewModel (private val app: Application) : AndroidViewModel(app) {
    private lateinit var registerUser: MutableLiveData<RegisterUserModel?>
   // private lateinit var profileUser: MutableLiveData<UserModel?>
    private lateinit var loginUser: MutableLiveData<LoginUserModel?>
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    var mRepository = UserRepository.getInstance()

    fun registerUser(userInput: RegisterUserInput): MutableLiveData<RegisterUserModel?> {
        if (NetworkeHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            registerUser = mRepository.registerUser(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, userInput)
        } else {
            mShowNetworkError.value = true
        }
        return registerUser
    }

    fun loginUser(username: String, password: String): MutableLiveData<LoginUserModel?> {
        if (NetworkeHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            loginUser = mRepository.loginUser(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, username, password)
        } else {
            mShowNetworkError.value = true
        }
        return loginUser
    }
}