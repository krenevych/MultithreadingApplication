package com.example.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {


    private val _factorialLD = MutableLiveData<String>()
    val factorialLD: LiveData<String>
        get() = _factorialLD

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress


    var job: Job? = null

    fun calculateFactorial(n: String?) {
        if (n.isNullOrEmpty()) {
            _error.value = true
            _progress.value = false
            return
        }

        _progress.value = true
        _error.value = false

        val n_int = n.toInt()

        job = viewModelScope.launch {

            val res = withContext(Dispatchers.Default) {
                factorial(n_int).toString()
            }


            _factorialLD.value = res
            _progress.value = false
        }

    }


    private fun factorial(n: Int): BigInteger {
        var f = BigInteger.ONE
        for (i in 1..n) {
            f *= i.toBigInteger()
        }
        return f
    }

    fun cancelCalculation() {
        job?.cancel()
        _progress.value = false
        _error.value = false
        _factorialLD.value = ""

    }
}