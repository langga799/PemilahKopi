package com.example.pemilahkopi.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pemilahkopi.R
import com.example.pemilahkopi.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar = binding.toolbar
        toolbar.apply {
            title = "Halaman Informasi"
            setTitleTextColor(ContextCompat.getColor(baseContext, R.color.white))
            navigationIcon =
                ContextCompat.getDrawable(baseContext, R.drawable.ic_round_arrow_back_24)
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }




        binding.apply {
            tvKuning.isEnabled = false
            tvHijau.isEnabled = false
            tvMerah.isEnabled = false

            tvHijau.setText("100")
            tvKuning.setText("100")
            tvMerah.setText("100")



            btnToRiwayat.setOnClickListener {
                startActivity(Intent(this@InformationActivity, RiwayatActivity::class.java))
            }


        }
    }


}