package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.presentation.recyclerview.UserListAdapter
import com.picpay.desafio.android.presentation.stateholder.MainStateHolder
import com.picpay.desafio.android.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserListAdapter

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val uiObserver = Observer<MainStateHolder> { state ->
        when (state) {
            is MainStateHolder.Loading -> {
                binding.userListProgressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }

            is MainStateHolder.Success -> {
                binding.userListProgressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.users = state.data
            }

            is MainStateHolder.Error -> {
                binding.userListProgressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = UserListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        val viewModel: MainViewModel by viewModel()
        with(viewModel) {
            uiState.observe(this@MainActivity, uiObserver)
            loadUsers()
        }
    }
}
