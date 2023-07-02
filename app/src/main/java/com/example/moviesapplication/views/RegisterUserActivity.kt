package com.example.moviesapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ActivityRegisterUserBinding
import com.example.moviesapplication.models.registeruser.RegisterUserInput
import com.example.moviesapplication.viewmodels.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityRegisterUserBinding
    private var userInput: RegisterUserInput = RegisterUserInput()
    private lateinit var etemail: TextInputEditText
    private lateinit var etpassword: TextInputEditText
    private lateinit var etname: TextInputEditText
    private lateinit var btregister: AppCompatButton
    private var email = ""
    private var password = ""
    private var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_user)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        etemail=findViewById(R.id.et_email)
        etpassword=findViewById(R.id.et_password)
        etname=findViewById(R.id.et_name)
        btregister=findViewById(R.id.bt_register)
        listener()
    }
    private fun listener() {
        binding.btRegister.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            name = binding.etName.text.toString()

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "همه فیلد ها باید وارد شوند ", Toast.LENGTH_LONG).show()
            } else {
                userInput = RegisterUserInput(email, password, name)
                userViewModel.registerUser(userInput).observe(this, Observer { user ->
                    Toast.makeText(
                        this@RegisterUserActivity,
                        "userId: ${user?.id.toString()}\n userEmail: ${user?.email}\n userCreateDate: ${user?.created_at}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    //  prefsUtils?.saveUser(email, password)
                    finish()
                })
                userViewModel.mShowApiError.observe(this, Observer {
                    AlertDialog.Builder(this).setMessage(it).show()
                })
                userViewModel.mShowProgressBar.observe(this, Observer { bt ->
                    if (bt) {
                        binding.progressBar.visibility = View.VISIBLE

                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                })
                userViewModel.mShowNetworkError.observe(this, Observer {
                    AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
                })
            }
        }
    }
}