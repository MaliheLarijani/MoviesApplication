package com.example.moviesapplication.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.R
import com.example.moviesapplication.adapters.GenresAdapter
import com.example.moviesapplication.adapters.MoviesAdapter
import com.example.moviesapplication.databinding.ActivityMainBinding
import com.example.moviesapplication.models.genres.GenresData
import com.example.moviesapplication.viewmodels.MoviesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton



class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding
    private var page: Int = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var floatingAddActionButton: FloatingActionButton
    private lateinit var appCompatImageView: AppCompatImageView
    private lateinit var progressBar: ProgressBar
    private var searchText:String=""
    private lateinit var editSearch: AppCompatEditText
    private lateinit var ivuser: AppCompatImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
            binding.viewModel = moviesViewModel
            binding.lifecycleOwner = this
            recyclerView=findViewById(R.id.rv_movies)
            floatingActionButton=findViewById(R.id.fab_refresh)
            floatingAddActionButton=findViewById(R.id.fab_addMovie)
            // appCompatImageView=findViewById(R.id.iv_user)
            progressBar=findViewById(R.id.progressBar)
            editSearch=findViewById(R.id.et_search)
            ivuser=findViewById(R.id.iv_user)
            initializeRecyclerView()
            initializeObservers()
            listener()




    }
    private fun initializeRecyclerView(){
        moviesAdapter= MoviesAdapter()
        genresAdapter = GenresAdapter()
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,false)
            adapter=moviesAdapter
        }
        binding.rvGenres.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genresAdapter
        }

    }
    private fun initializeObservers(){
        moviesViewModel.getGenresMovie(false,genresId = genresId, page = page)
            .observe(this, Observer { movie ->
                moviesAdapter.setData(movie?.data!!, this@MainActivity)
            })
        moviesViewModel.getMovies(false).observe(this, Observer { movie ->
            moviesAdapter.setData(movie?.data!!, this@MainActivity)
        })
        moviesViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        moviesViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                binding.progressBar.visibility = View.VISIBLE
                binding.fabRefresh.hide()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.fabRefresh.show()
            }
        })
        moviesViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })

        moviesViewModel.getGenres().observe(this, Observer { genres ->
            genresAdapter.setData(genres as List<GenresData>, this@MainActivity, moviesViewModel)
        })


    }
    private fun listener(){
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && p0.isNotEmpty() && p0.length > 2) {
                    searchText = p0.toString()
                    moviesViewModel.searchMovies(true, searchText, page)
                        .observe(this@MainActivity, Observer { movie ->
                            moviesAdapter.setData(movie?.data!!, this@MainActivity)
                        })
                    moviesViewModel.mShowApiError.observe(this@MainActivity, Observer {
                        AlertDialog.Builder(this@MainActivity).setMessage(it).show()
                    })
                    moviesViewModel.mShowProgressBar.observe(this@MainActivity, Observer { bt ->
                        if (bt) {
                            binding.progressBar.visibility = View.VISIBLE
                            //binding.floatingActionButton.hide()
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.fabRefresh.show()
                        }
                    })
                    moviesViewModel.mShowNetworkError.observe(this@MainActivity, Observer {
                        AlertDialog.Builder(this@MainActivity)
                            .setMessage(R.string.app_no_internet_msg).show()
                    })
                } else if (p0!!.isEmpty()) {
                    moviesViewModel.getMovies(true)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.fabAddMovie.setOnClickListener {
            var intent = Intent(this@MainActivity, AddMoviesActivity::class.java)
            startActivity(intent)
        }
        binding.ivUser.setOnClickListener {
            var intent = Intent(this@MainActivity, RegisterUserActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        var genresId: Int = 0
        var page: Int = 0
    }
    }
