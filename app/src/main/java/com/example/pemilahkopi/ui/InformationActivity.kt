package com.example.pemilahkopi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pemilahkopi.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvKuning.isEnabled = false
            tvHijau.isEnabled = false
            tvMerah.isEnabled = false

            tvHijau.setText("100")
            tvKuning.setText("100")
            tvMerah.setText("100")
        }
    }
}