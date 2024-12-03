package com.example.flowexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.flowexercise.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var sharedViewModel: SharedFlowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // stateFlowFunction()
       // sharedFlowFunction()



    }

    private fun sharedFlowFunction(){
        /**
         * Shared Flow : HardFlow Örneğidir.
         * sare flowda initial değer verilmesi zorunlu değildir
         * en öenmli özelliği -> one time event'tir tek bir değer tutmak zorunda değildir,
         * birden fazla collector'dan gelen veriyi simultane şekilde emit edebilir.
         * one time event'tir. rotate ettiğimizde ekranı yeniden değer başlamaz aldıgı değeri devam eder
         */
        sharedViewModel = ViewModelProvider(this)[SharedFlowViewModel::class.java]
        collectLifecycleFlow(sharedViewModel.sharedFlow){
            binding.textView.text = it.toString()
        }

    }


    private fun stateFlowFunction(){
        /**
         * State Flow : HardFlow Örneğidir. Yani collector olmasada işe yarar. Adından anlaşıldıgı gibi state'dir
         * LiveData tek farkı lifecycle aware olmaması
         * Collect vs CollectLatest
         * Collect -> emit edilen tüm datayı elde ederiz
         * CollectLatest -> emit edilen en son datayı alır,
         * örnek sürekli değişen uı için önerilir burda yaptıgımız geri sayım gibi, Memory faydası sağlar
         */
        viewModel= ViewModelProvider(this)[MyViewModel::class.java]
        collectLifecycleFlow(viewModel.collectSeconds){
            binding.textView.text = it.toString()
        }
    }

    fun <T> ComponentActivity.collectLifecycleFlow(flow :Flow<T> , collect : suspend (T) -> Unit ){
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                flow.collectLatest(collect)
            }

        }
    }
}