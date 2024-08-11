package com.adika.palindromeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel () {

    private val _isPalindrome = MutableLiveData<Boolean>()
    val isPalindrome: LiveData<Boolean> get() = _isPalindrome

    fun checkPalindrome(sentence: String) {
        val cleanSentence = sentence.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase()
        _isPalindrome.value = cleanSentence == cleanSentence.reversed()
    }
}