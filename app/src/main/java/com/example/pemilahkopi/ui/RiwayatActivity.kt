package com.example.pemilahkopi.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pemilahkopi.R
import com.example.pemilahkopi.adapter.TableAdapter
import com.example.pemilahkopi.databinding.ActivityRiwayatBinding
import com.example.pemilahkopi.model.DataKopiResponse
import com.example.pemilahkopi.network.RetrofitBuilder
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private val adapter = TableAdapter()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvItemTable.setHasFixedSize(true)


        // GET data from API
        binding.progressLoading.visibility = View.VISIBLE
        RetrofitBuilder().getApiService().getDataKopi()
            .enqueue(object : Callback<DataKopiResponse> {
                override fun onResponse(
                    call: Call<DataKopiResponse>,
                    response: Response<DataKopiResponse>,
                ) {
                    when (response.code()) {
                        200 -> {
                            val dataRiwayat = response.body()?.riwayat
                            dataRiwayat?.let { riwayatItem ->
                                adapter.addData(riwayatItem)
                            }
                            binding.progressLoading.visibility = View.GONE

                            binding.rvItemTable.adapter = adapter
                        }

                        400 -> {
                            Log.d("FAILED", response.message())
                            binding.progressLoading.visibility = View.GONE
                        }
                    }

                }

                override fun onFailure(call: Call<DataKopiResponse>, t: Throwable) {
                    Toast.makeText(this@RiwayatActivity, t.message, Toast.LENGTH_SHORT).show()
                    binding.progressLoading.visibility = View.GONE
                }

            })




        binding.btnDownloadPdf.setOnClickListener {
            val dateNow = Date().time
            val format = SimpleDateFormat("dd_MM_yyyy-HH_mm")
            val newDate = format.format(dateNow)

            PdfGenerator.Builder()
                .setContext(this@RiwayatActivity)
                .fromViewIDSource()
                .fromViewID(this@RiwayatActivity, R.id.tables)
                .setFileName("DataKopi-$newDate")
                .setFolderNameOrPath("Data Kopi")
                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
                .build(object : PdfGeneratorListener() {
                    override fun onStartPDFGeneration() {
                        Toast.makeText(this@RiwayatActivity,
                            "Data berhasil diunduh",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onFinishPDFGeneration() {
                        Toast.makeText(this@RiwayatActivity,
                            "Berhasil membuat PDF",
                            Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

}