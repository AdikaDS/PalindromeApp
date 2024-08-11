package com.adika.palindromeapp.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adika.palindromeapp.databinding.ActivityThirdScreenBinding
import com.adika.palindromeapp.viewmodel.ThirdViewModel

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var usersAdapter: ListUsersAdapter
    private val viewModel: ThirdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersAdapter = ListUsersAdapter { user ->
            val resultIntent = Intent().apply {
                putExtra("SELECTED_USER_NAME", "${user.firstName} ${user.lastName}")
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.rvUsers.adapter = usersAdapter
        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        viewModel.userList.observe(this) { users ->
            usersAdapter.submitList(users)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.isError.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.resetPage()
        }

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!viewModel.isLoading.value!! && totalItemCount <= lastVisibleItemPosition + 5) {
                    viewModel.loadNextPage()
                }
            }
        })

        binding.btnBackArrow.setOnClickListener {
            val intent = Intent(this, SecondScreen::class.java)
            startActivity(intent)
            finish()
        }

        viewModel.loadUsers()
    }
}