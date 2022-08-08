package com.example.pemilahkopi.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pemilahkopi.R
import com.example.pemilahkopi.databinding.ActivityControlBinding
import com.example.pemilahkopi.model.ControlMesinResponse
import com.example.pemilahkopi.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar = binding.toolbar
        toolbar.apply {
            title = "Halaman Kontrol"
            setTitleTextColor(ContextCompat.getColor(baseContext, R.color.white))
            navigationIcon =
                ContextCompat.getDrawable(baseContext, R.drawable.ic_round_arrow_back_24)
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        binding.apply {
            btnOn.setOnClickListener {
                RetrofitBuilder().getApiService().setControlMesin(1)
                    .enqueue(object : Callback<ControlMesinResponse> {
                        override fun onResponse(
                            call: Call<ControlMesinResponse>,
                            response: Response<ControlMesinResponse>,
                        ) {
                            when (response.code()) {
                                200 -> {
                                    Toast.makeText(this@ControlActivity,
                                        "Mesin Dihidupkan",
                                        Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    Toast.makeText(this@ControlActivity,
                                        "Mesin Gagal Dihidupkan",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ControlMesinResponse>, t: Throwable) {

                        }

                    })
            }

            btnOff.setOnClickListener {
                RetrofitBuilder().getApiService().setControlMesin(0)
                    .enqueue(object : Callback<ControlMesinResponse> {
                        override fun onResponse(
                            call: Call<ControlMesinResponse>,
                            response: Response<ControlMesinResponse>,
                        ) {
                            when (response.code()) {
                                200 -> {
                                    Toast.makeText(this@ControlActivity,
                                        "Mesin Dimatikan",
                                        Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    Toast.makeText(this@ControlActivity,
                                        "Mesin Gagal Dimatikan",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ControlMesinResponse>, t: Throwable) {

                        }

                    })
            }
        }


    }
}