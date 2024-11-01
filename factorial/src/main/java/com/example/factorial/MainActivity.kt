package com.example.factorial

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.factorial.databinding.ActivityMainBinding
import com.example.factorial.state.Error
import com.example.factorial.state.Factorial
import com.example.factorial.state.Progress
import com.example.factorial.state.State

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
            binding.tvFactorial.text = ""
            val n = binding.etNum.text.toString()
            viewModel.calculateFactorial(n)
        }

        binding.btnCancel.setOnClickListener {
            viewModel.cancelCalculation()
        }

        viewModel.state.observe(this) { state: State ->
            binding.progressBar.visibility = View.GONE
            binding.etNum.isEnabled = true
            binding.btnCalcFactorial.isEnabled = true
            binding.btnCancel.isEnabled = false


            when (state) {
                Error ->  // по опeрації порівняння  // state == Error
                    Toast.makeText(this, "Please enter valid integer!", Toast.LENGTH_SHORT).show()

                Progress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.etNum.isEnabled = false
                    binding.btnCalcFactorial.isEnabled = false
                    binding.btnCancel.isEnabled = true
                }

                is Factorial -> binding.tvFactorial.text = state.value  // state is Factorial
            }
        }

    }


}