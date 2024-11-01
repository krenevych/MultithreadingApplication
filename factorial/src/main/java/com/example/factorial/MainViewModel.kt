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


    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    var job: Job? = null

    fun calculateFactorial(n: String?) {
        if (n.isNullOrEmpty()) {
            _state.value = State(error = true)
            return
        }

        _state.value = State(progress = true)

        val n_int = n.toInt()

        job = viewModelScope.launch {

            val res = withContext(Dispatchers.Default) {
                factorial(n_int).toString()
            }

            _state.value = State(factorial = res)
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
        _state.value = State()
        job?.cancel()
        job = null
    }

}