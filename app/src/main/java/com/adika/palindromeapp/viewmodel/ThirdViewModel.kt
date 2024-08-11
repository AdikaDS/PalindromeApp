package com.adika.palindromeapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adika.palindromeapp.api.response.DataItem
import com.adika.palindromeapp.api.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ThirdViewModel : ViewModel () {
    private val _userList = MutableLiveData<List<DataItem>>()
    val userList: LiveData<List<DataItem>> get() = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<String?>()
    val isError: LiveData<String?> get() = _isError

    private var currentPage = 1
    private val perPage = 6
    private val apiService = ApiConfig.getApiService()

    fun loadUsers(isNextPage: Boolean = false) {
        if (isNextPage) currentPage++

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getUsers(currentPage, perPage)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val newList = if (isNextPage) {
                            (_userList.value ?: emptyList()) + (it.data?.filterNotNull() ?: emptyList())
                        } else {
                            it.data?.filterNotNull() ?: emptyList()
                        }
                        _userList.value = newList
                    }
                } else {
                    _isError.value = "Failed to load users: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error load users: ${e.message}")
                _isError.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetPage() {
        currentPage = 1
        loadUsers()
    }

    fun loadNextPage() {
        loadUsers(isNextPage = true)
    }

    companion object {
        private const val TAG = "ThirdViewModel"
    }
}