package com.adika.palindromeapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.adika.palindromeapp.databinding.ActivityFirstScreenBinding
import com.adika.palindromeapp.viewmodel.FirstViewModel

class FirstScreen : AppCompatActivity() {

    private val viewModel: FirstViewModel by viewModels()
    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.isPalindrome.observe(this) { isPalindrome ->
            showPalindromeDialog(isPalindrome)
        }


        binding.btnCheck.setOnClickListener {
            val sentence = binding.etPalindrome.text.toString()
            viewModel.checkPalindrome(sentence)
        }

        binding.btnNext.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isNotBlank()) {
                val intent = Intent(this, SecondScreen::class.java)
                intent.putExtra("USERNAME", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPalindromeDialog(isPalindrome: Boolean) {
        val message = if (isPalindrome) "isPalindrome" else "not Palindrome"
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}