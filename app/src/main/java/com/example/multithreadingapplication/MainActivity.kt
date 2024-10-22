package com.example.multithreadingapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreadingapplication.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            loadData()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {

        Log.d(TAG, "loadData: start data loading from internet...")

        // TODO:

        // on Start:
            // disable button "load data"
        binding.btnLoadData.isEnabled = false
            // show progress bar
        binding.progressBar.visibility = View.VISIBLE

            // clear City
        binding.tvCityValue.text = ""
            // clear Temperature
        binding.tvTemperatureValue.text = ""
            // show Toast that data is started loading.

        // on Progress
            // load City
        loadCity { city ->
            handler.post{
                // and set it into correspondent text view
                binding.tvCityValue.text = city
            }

            // then load temperature for loaded City,
            loadTemperature(city) { temperature ->

                handler.post {
                    // and set it into correspondent text view
                    binding.tvTemperatureValue.text = temperature.toString()

                    // on Finish:
                    // hide progress bar
                    binding.progressBar.visibility = View.GONE

                    // enable button "load data"
                    binding.btnLoadData.isEnabled = true
                }

            }
        }

    }

    private fun loadCity(callback : (String) -> Unit) {
        thread {
            Thread.sleep(3_000)  // simulate loading...
            callback("Kyiv")   // return "Kyiv"
        }

    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            Log.d(TAG, "loadTemperature: for city $city")
            Thread.sleep(3_000)  // simulate loading...
            callback(9) // return 9
        }


    }

    companion object {
        val TAG = "XXXX"
    }
}