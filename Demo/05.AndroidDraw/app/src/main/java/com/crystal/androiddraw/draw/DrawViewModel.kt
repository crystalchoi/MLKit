package com.crystal.androiddraw.draw

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.crystal.androiddraw.tflite.Classifier

class DrawViewModel(private val classifier: Classifier) : ViewModel() {
    private val _result: MutableState<String> = mutableStateOf("...")

    val result: State<String>
        get() = _result as State<String>

    fun updateResult(digitString: String) {
        _result.value = digitString
    }
}