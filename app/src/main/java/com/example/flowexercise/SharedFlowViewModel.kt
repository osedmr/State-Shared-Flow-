package com.example.flowexercise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SharedFlowViewModel : ViewModel() {
    val TAG="FLOW"

    private val _sharedFlow = MutableSharedFlow<Int>(replay = 5)
    val sharedFlow = _sharedFlow.asSharedFlow()

    /**
     * Replay -> sana subscribe olmak isteyen , observe etmek isteyen yeni collector olursa, onlar önceden emit edilen değerleri erişebilir.
     * yeni bir collector var ise yani yeni bir subscriber işin içine girdiyse
     */
    init {
        // Replay sana subscribe olmak istiyen ,observe etmek isteyen yeni collector olursa, onlar önceden emit edilen değerlere eişebilir.


        emitValue()
    }

    private fun emitValue(){
        viewModelScope.launch {
            for (i in 1..10){
                delay(1000L)
                _sharedFlow.emit(i)

            }
        }
    }
}