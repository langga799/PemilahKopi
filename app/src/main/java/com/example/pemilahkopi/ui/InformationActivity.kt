package com.example.pemilahkopi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pemilahkopi.R
import com.example.pemilahkopi.databinding.ActivityInformationBinding
import com.example.pemilahkopi.model.DataBeratResponse
import com.example.pemilahkopi.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


        RetrofitBuilder().getApiService().getDataBerat()
            .enqueue(object : Callback<DataBeratResponse> {
                override fun onResponse(
                    call: Call<DataBeratResponse>,
                    response: Response<DataBeratResponse>,
                ) {
                    if (response.isSuccessful) {
                        Log.d("DATA_BERAT", response.body().toString())
                        val data = response.body()?.kopis!!

                        for (berat in data){

                        }

                        binding.apply {
                            tvHijau.setText(data[2].berat.toString())
                            tvKuning.setText(data[1].berat.toString())
                            tvMerah.setText(data[0].berat.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<DataBeratResponse>, t: Throwable) {

                }

            })


        binding.apply {
            tvKuning.isEnabled = false
            tvHijau.isEnabled = false
            tvMerah.isEnabled = false





            btnToRiwayat.setOnClickListener {
                startActivity(Intent(this@InformationActivity, RiwayatActivity::class.java))
            }


        }
    }


}