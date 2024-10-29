package com.example.factorial

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
            val n = binding.etNum.text.toString()
            viewModel.calculateFactorial(n)
        }

        viewModel.factorialLD.observe(this) { fact: String ->
            binding.tvFactorial.text = fact
        }

    }


}