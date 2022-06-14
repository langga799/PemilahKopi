package com.example.pemilahkopi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pemilahkopi.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation()
    }

    private fun navigation(){
        binding.apply {
            btnToControl.setOnClickListener {
                startActivity(Intent(this@HomeActivity, ControlActivity::class.java))
            }

            btnToInformation.setOnClickListener {
                startActivity(Intent(this@HomeActivity, InformationActivity::class.java))
            }
        }
    }
}