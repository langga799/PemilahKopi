package com.example.pemilahkopi.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pemilahkopi.R
import com.example.pemilahkopi.databinding.ActivityInformationBinding
import com.example.pemilahkopi.databinding.ActivityMainBinding
import com.example.pemilahkopi.databinding.ItemInformationBinding
import com.example.pemilahkopi.model.DataBeratResponse
import com.example.pemilahkopi.model.ResetKopiResponse
import com.example.pemilahkopi.network.RetrofitBuilder
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.tan

class InformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationBinding

    private var labelHijau = ""
    private var labelKuning = ""
    private var labelMerah = ""
    private var labelCurrentDate = ""

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val currentDate = Date().time
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val newDate = format.format(currentDate)
        labelCurrentDate = newDate



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



        // Get Data Berat
        RetrofitBuilder().getApiService().getDataBerat()
            .enqueue(object : Callback<DataBeratResponse> {
                override fun onResponse(
                    call: Call<DataBeratResponse>,
                    response: Response<DataBeratResponse>,
                ) {
                    if (response.isSuccessful) {
                        Log.d("DATA_BERAT", response.body().toString())
                        val data = response.body()?.kopis!!

                        labelHijau = data[2].berat.toString()
                        labelKuning = data[1].berat.toString()
                        labelMerah = data[0].berat.toString()

                        val hijau = findViewById<TextView>(R.id.labelHijau)
                        val kuning = findViewById<TextView>(R.id.labelKuning)
                        val merah = findViewById<TextView>(R.id.labelMerah)
                        val tanggal = findViewById<TextView>(R.id.tanggalSekarang)

                        hijau.text = labelHijau
                        kuning.text = labelKuning
                        merah.text = labelMerah
                        tanggal.text = labelCurrentDate


                        Log.d("DATA", labelHijau + labelKuning + labelMerah)

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


        // Reset Data Kopi
        binding.btnReset.setOnClickListener {
            RetrofitBuilder().getApiService().resetDataKopi()
                .enqueue(object : Callback<ResetKopiResponse> {
                    override fun onResponse(
                        call: Call<ResetKopiResponse>,
                        response: Response<ResetKopiResponse>,
                    ) {
                        if (response.isSuccessful) {
                            finish()
                            startActivity(intent)
                            Toast.makeText(this@InformationActivity,
                                "Data Berat Kopi Berhasil Di Reset",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResetKopiResponse>, t: Throwable) {

                    }

                })
        }


        binding.btnDownloadDataBerat.setOnClickListener {
            val dateNow = Date().time
            val formatDate = SimpleDateFormat("dd_MM_yyyy-HH_mm")
            val newCurrentDate = formatDate.format(dateNow)

            PdfGenerator.Builder()
                .setContext(this@InformationActivity)
                .fromViewIDSource()
                .fromViewID(this@InformationActivity, R.id.informationDataBerat)
                .setFileName("DataKopi-$newCurrentDate")
                .setFolderNameOrPath("Data Berat Sortir Kopi")
                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
                .build(object : PdfGeneratorListener() {
                    override fun onStartPDFGeneration() {
                        Toast.makeText(this@InformationActivity,
                            "Data berhasil diunduh",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onFinishPDFGeneration() {
                        Toast.makeText(this@InformationActivity,
                            "Berhasil membuat PDF",
                            Toast.LENGTH_SHORT).show()
                    }

                })
        }


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