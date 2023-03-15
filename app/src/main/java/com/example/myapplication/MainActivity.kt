package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.NetworkResult
import com.example.myapplication.view.DataAdapter
import com.example.myapplication.view.DetailActivity
import com.example.myapplication.viewModel.ApiDataViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ApiDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loading = true
        binding.executePendingBindings()

        viewModel.data.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.loading = false
                    binding.rvData.adapter = DataAdapter(it.data) { model ->
                        startActivity(Intent(this, DetailActivity::class.java).apply {
                            putExtra("data", model)
                        })
                    }
                }
                is NetworkResult.Error -> {
                    binding.loading = false
                    Snackbar.make(binding.root, it.message ?: "", Snackbar.LENGTH_LONG).show()
                }
                else -> binding.loading = false
            }
        }

        viewModel.fetchData()
    }
}

