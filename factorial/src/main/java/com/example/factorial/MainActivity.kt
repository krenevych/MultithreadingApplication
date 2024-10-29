package com.example.factorial

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.factorial.databinding.ActivityMainBinding
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(binding.root)

        binding.btnCalcFactorial.setOnClickListener {
//            val f = calculateFactorial(binding.etNum.text.toString().toInt())
//            binding.tvFactorial.text = f.toString()
//            val viewModel1: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//            viewModel
//            Log.d("XXX", "onCreate: ")
            binding.tvFactorial.text = ""
            val n = binding.etNum.text.toString()
            viewModel.calculateFactorial(n)
        }

        binding.btnCancel.setOnClickListener {
            viewModel.cancelCalculation()
        }

        viewModel.factorialLD.observe(this) { fact: String ->
            binding.tvFactorial.text = fact
        }

        viewModel.error.observe(this) { err: Boolean ->
            if (err)
                Toast.makeText(this, "Please enter valid integer!", Toast.LENGTH_SHORT).show()
        }

        viewModel.progress.observe(this) { inProgress: Boolean ->
            if (inProgress) {
                binding.progressBar.visibility = View.VISIBLE
                binding.etNum.isEnabled = false
                binding.btnCalcFactorial.isEnabled = false
                binding.btnCancel.isEnabled = true
            } else {
                binding.progressBar.visibility = View.GONE
                binding.etNum.isEnabled = true
                binding.btnCalcFactorial.isEnabled = true
                binding.btnCancel.isEnabled = false
            }
        }

    }


}