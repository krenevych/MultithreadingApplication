package com.example.factorial

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.factorial.databinding.ActivityMainBinding
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnCalcFactorial.setOnClickListener {
            val f = calculateFactorial(binding.etNum.text.toString().toInt())
            binding.tvFactorial.text = f.toString()
        }
    }


    private fun calculateFactorial(n: Int): BigInteger {
        var f = BigInteger.ONE
        for (i in 1..n) {
            f *= i.toBigInteger()
        }
        return f
    }
}