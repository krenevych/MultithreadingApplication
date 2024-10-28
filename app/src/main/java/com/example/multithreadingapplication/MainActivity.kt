package com.example.multithreadingapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.multithreadingapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
//            lifecycleScope.launch {
                loadData()
//            }

//            val coroutineScope = CoroutineScope(Dispatchers.Main)
//            coroutineScope.launch {
//                loadData()
//            }

        }

    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {

        Log.d(TAG, "loadData: start ${this@MainActivity}")

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
        val jobCity: Deferred<String> = lifecycleScope.async (Dispatchers.IO) {
            // load City
            val city: String = loadCity()

            city  // return city
        }


        val jobTemperature: Deferred<Int> = lifecycleScope.async(Dispatchers.IO) {
            // then load temperature for loaded City,
            val temperature: Int = loadTemperature()

            temperature
        }

        lifecycleScope.launch {
//            jobCity.join()
//            jobTemperature.join()

            val city: String = jobCity.await()
            val temperature: Int = jobTemperature.await()

            // and set it into correspondent text view

            binding.tvCityValue.text = city
            // and set it into correspondent text view
            binding.tvTemperatureValue.text = temperature.toString()


            // on Finish:
            // hide progress bar

            // we reach this point after jobCity and jobTemperature have finished their duties
            binding.progressBar.visibility = View.GONE

            // enable button "load data"
            binding.btnLoadData.isEnabled = true

            Log.d(TAG, "loadData: finish ${this@MainActivity}")
        }


    }

    private suspend fun loadCity(): String {
//        withContext(Dispatchers.IO) {
//            Thread.sleep(3_000)
//        }  // simulate loading...

        val delay_time = Random.nextInt(2_000, 4_000).toLong()

        delay(delay_time)

        return "Kyiv"
    }

    private suspend fun loadTemperature(): Int {
//        Thread.sleep(3_000)  // simulate loading...
//        delay(3_000)

        val delay_time = Random.nextInt(2_000, 4_000).toLong()

        delay(delay_time)

        return 9
    }



    companion object {
        val TAG = "XXXX"
    }
}