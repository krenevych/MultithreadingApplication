package com.example.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigInteger

class MainViewModel : ViewModel() {


    private val _factorialLD = MutableLiveData<String>()
    val factorialLD : LiveData<String>
        get() = _factorialLD


    fun calculateFactorial(n: String?){
        val n_int = n?.toInt()
        val res = factorial(n_int!!).toString()
        _factorialLD.value = res
    }


    private fun factorial(n: Int): BigInteger {
        var f = BigInteger.ONE
        for (i in 1..n) {
            f *= i.toBigInteger()
        }
        return f
    }
}