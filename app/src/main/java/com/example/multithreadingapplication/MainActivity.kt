package com.example.multithreadingapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreadingapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        val city: String = loadCity()
               // and set it into correspondent text view
        binding.tvCityValue.text = city
            // then load temperature for loaded City,
        val temperature: Int = loadTemperature(city)
               // and set it into correspondent text view
        binding.tvTemperatureValue.text = temperature.toString()

        // on Finish:
            // hide progress bar
        binding.progressBar.visibility = View.GONE

            // enable button "load data"
        binding.btnLoadData.isEnabled = true
    }

    private fun loadCity(): String {
        Thread.sleep(3_000)  // simulate loading...

        return "Kyiv"
    }

    private fun loadTemperature(city: String): Int {
        Thread.sleep(3_000)  // simulate loading...
        return 9
    }

    companion object {
        val TAG = "XXXX"
    }
}