package com.example.moviesapplication.repositorys

import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.models.login.LoginUserModel
import com.example.moviesapplication.models.registeruser.RegisterUserInput
import com.example.moviesapplication.models.registeruser.RegisterUserModel
import com.example.moviesapplication.retrofitapi.NetworkResponseCallback
import com.example.moviesapplication.retrofitapi.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(){
    private lateinit var mCallback: NetworkResponseCallback
    private var registerUser: MutableLiveData<RegisterUserModel?> =
        MutableLiveData<RegisterUserModel?>()
    private var loginUser: MutableLiveData<LoginUserModel?> =
        MutableLiveData<LoginUserModel?>()
    private lateinit var registerUserCall: Call<RegisterUserModel>
    private lateinit var loginUserCall: Call<LoginUserModel>

    fun registerUser(
        callback: NetworkResponseCallback,
        userInput: RegisterUserInput
    ): MutableLiveData<RegisterUserModel?> {
        mCallback = callback
        if (registerUser.value != null) {
            mCallback.onResponseSuccess()
            return registerUser
        }
        registerUserCall = RestClient.getInstance().getApiService().registerUser(userInput)
        registerUserCall.enqueue(object : Callback<RegisterUserModel> {
            override fun onResponse(
                call: Call<RegisterUserModel>,
                response: Response<RegisterUserModel>
            ) {
                registerUser.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<RegisterUserModel>, t: Throwable) {
                registerUser.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return registerUser
    }


    fun loginUser(
        callback: NetworkResponseCallback,
        username: String,
        password: String
    ): MutableLiveData<LoginUserModel?> {
        mCallback = callback
        if (loginUser.value != null) {
            mCallback.onResponseSuccess()
            return loginUser
        }
        loginUserCall = RestClient.getInstance().getApiService()
            .loginUser(grantType = "password", username, password)
        loginUserCall.enqueue(object : Callback<LoginUserModel> {
            override fun onResponse(
                call: Call<LoginUserModel>,
                response: Response<LoginUserModel>
            ) {
                loginUser.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<LoginUserModel>, t: Throwable) {
                loginUser.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return loginUser
    }

    companion object {
        private var mInstance: UserRepository? = null
        fun getInstance(): UserRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = UserRepository()
                }
            }
            return mInstance!!
        }
    }
}