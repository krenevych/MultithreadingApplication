package com.example.multithreadingapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreadingapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
//            loadDataWithoutCoroutine(1)

        }

    }

    @SuppressLint("SetTextI18n")
    private fun loadDataWithoutCoroutine(step: Int, parameter: Any? = null) {

        Log.d(TAG, "loadData: start ${this@MainActivity}")

        when (step) {
            1 -> { // on Start:
                // disable button "load data"
                binding.btnLoadData.isEnabled = false
                // show progress bar
                binding.progressBar.visibility = View.VISIBLE

                // clear City
                binding.tvCityValue.text = ""
                // clear Temperature
                binding.tvTemperatureValue.text = ""
                // show Toast that data is started loading.
                Toast.makeText(this, "Data loading started", Toast.LENGTH_SHORT).show()


                // on Progress
                // load City
                loadCityWithoutCoroutine { city: String ->
                    runOnUiThread {
                        loadDataWithoutCoroutine(2, city)
                    }
                }
            }

            2 -> {
                // and set it into correspondent text view
                val city = (parameter as String)
                binding.tvCityValue.text = city
                // then load temperature for loaded City,
                loadTemperatureWithoutCoroutine(city) { temp: Int ->
                    runOnUiThread {
                        loadDataWithoutCoroutine(3, temp)
                    }
                }
            }

            3 -> {
                val temperature = (parameter as Int)
                // and set it into correspondent text view
                binding.tvTemperatureValue.text = temperature.toString()
                // on Finish:
                // hide progress bar
                binding.progressBar.visibility = View.GONE

                // enable button "load data"
                binding.btnLoadData.isEnabled = true

                Log.d(TAG, "loadData: finish ${this@MainActivity}")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun loadData() {

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
        // load City

        val city: String = loadCityWithoutCoroutineWrapper()
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

        Log.d(TAG, "loadData: finish ${this@MainActivity}")
    }

    private suspend fun loadCity(): String {
//        Thread.sleep(3_000)  // simulate loading...
        delay(3_000)

        return "Kyiv"
    }

    private suspend fun loadTemperature(city: String): Int {
//        Thread.sleep(3_000)  // simulate loading...
        delay(3_000)

        return 9
    }

    private suspend fun loadCityWithoutCoroutineWrapper() : String {
        return suspendCoroutine { it: Continuation<String> ->
            loadCityWithoutCoroutine { city ->
                it.resumeWith(Result.success(city))
            }
        }
    }

    private fun loadCityWithoutCoroutine(callback: (String) -> Unit) {
        thread {
            Thread.sleep(3_000)  // simulate loading...
            callback("Kyiv")
        }

    }

    private fun loadTemperatureWithoutCoroutine(city: String, callback: (Int) -> Unit) {
        thread {
            Thread.sleep(3_000)  // simulate loading...
            callback(9)
        }

    }


    companion object {
        val TAG = "XXXX"
    }
}