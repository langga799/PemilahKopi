package com.example.pemilahkopi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pemilahkopi.databinding.ActivityControlBinding

class ControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBackToHome.setOnClickListener {
                startActivity(Intent(this@ControlActivity, HomeActivity::class.java))
                finishAffinity()
            }
        }

    }
}