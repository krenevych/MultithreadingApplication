package com.example.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.math.BigInteger

class MainViewModel : ViewModel() {


    private val _factorialLD = MutableLiveData<String>()
    val factorialLD : LiveData<String>
        get() = _factorialLD

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean>
        get() = _error

    private val _progress = MutableLiveData<Boolean>()
    val progress : LiveData<Boolean>
        get() = _progress


    val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun calculateFactorial(n: String?){
        if (n.isNullOrEmpty()) {
            _error.value = true
            _progress.value = false
            return
        }

        _progress.value = true
        _error.value = false

        val n_int = n.toInt()

        coroutineScope.launch {
            val res = factorial(n_int).toString()
            _factorialLD.postValue(res)  // postValue - setup data in Main thread.
            _progress.postValue(false)
//            _factorialLD.value = res
//            _progress.value = false
        }

    }


    override fun onCleared() {
        super.onCleared()

        coroutineScope.cancel()
    }

    fun cancelCalculation() {
        coroutineScope.cancel()
        _progress.postValue(false)
    }

    private fun factorial(n: Int): BigInteger {
        var f = BigInteger.ONE
        for (i in 1..n) {
            f *= i.toBigInteger()
        }
        return f
    }
}