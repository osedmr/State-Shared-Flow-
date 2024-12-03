package com.example.flowexercise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel()  {

    private val _collectedSeceonds = MutableStateFlow(10)
    val collectSeconds = _collectedSeceonds

    val TAG="FLOW"

    init {
        collectSeconds()
    }

    val seconds = flow<Int> { //coldflow -> eğer herhangi bir noktada collect edilmezse hiç bir işe yaramayan bir flow çeşitidir
        val startingValue = 20
        var currentValue = startingValue

        emit(startingValue)
        while (currentValue > 0){

            delay(1000L)
            currentValue--
            emit(currentValue)

        }
    }


    private fun collectSeconds(){
        viewModelScope.launch(Dispatchers.Main) {
            seconds.collect{seconds->
                _collectedSeceonds.value = seconds
                Log.e(TAG,"Kalan süre $seconds")
            }
        }
    }
}