package com.adika.palindromeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondViewModel : ViewModel () {
    private val _showName = MutableLiveData<String>()
    val showName: LiveData<String> get() = _showName

    private val _selectedUserName = MutableLiveData<String>()
    val selectedUserName: LiveData<String> get() = _selectedUserName

    fun setName(name: String) {
        _showName.value = name
    }

    fun setSelectedUserName(name: String) {
        _selectedUserName.value = name
    }
}