package com.indra.coinbaseapp.coinbase.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.indra.coinbaseapp.CoinbaseApp
import com.indra.coinbaseapp.coinbase.viewmodel.CoinbaseViewModel
import com.indra.coinbaseapp.databinding.ActivityMainBinding

class CoinbaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Dagger is throwing error, there seems to be lib compatibility between moshi and dagger
        //(application as AppComponent.ComponentProvider).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val coinbaseService = (this.application as CoinbaseApp).coinbaseService
        val viewModel = CoinbaseViewModel(coinbaseService)
        viewModel.connect()
    }
}