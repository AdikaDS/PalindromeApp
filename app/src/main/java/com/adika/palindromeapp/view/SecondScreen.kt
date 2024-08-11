package com.adika.palindromeapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.adika.palindromeapp.databinding.ActivitySecondScreenBinding
import com.adika.palindromeapp.viewmodel.SecondViewModel

class SecondScreen : AppCompatActivity() {

    private val viewModel: SecondViewModel by viewModels()
    private lateinit var binding: ActivitySecondScreenBinding

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedUserName = result.data?.getStringExtra("SELECTED_USER_NAME")
                viewModel.setSelectedUserName(selectedUserName ?: "No user selected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("USERNAME")
        viewModel.setName(name ?: "Unknown User")

        viewModel.showName.observe(this) { showName ->
            binding.tvName.text = showName
        }

        viewModel.selectedUserName.observe(this) { userName ->
            binding.tvSelectedUser.text = "Selected User: $userName"
        }

        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            getResult.launch(intent)
        }

        binding.btnBackArrow.setOnClickListener {
            val intent = Intent(this, FirstScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}