package com.example.factorial

//data class State (
//    val factorial: String = "",
//    val error: Boolean = false,
//    val progress: Boolean = false
//)

//enum class State(val value: String) {
//    Error(""),
//    Progress(""),
//    Factorial(""),
//}

abstract class State

class Error: State()
class Progress: State()
class Factorial(
    val value: String
): State()

