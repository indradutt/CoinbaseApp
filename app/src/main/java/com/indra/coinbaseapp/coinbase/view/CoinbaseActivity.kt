package com.indra.coinbaseapp.coinbase.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.indra.coinbaseapp.CoinbaseApp
import com.indra.coinbaseapp.coinbase.viewmodel.CoinbaseViewModel
import com.indra.coinbaseapp.databinding.ActivityMainBinding
import javax.inject.Inject

class CoinbaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var coinbaseViewModel: CoinbaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as CoinbaseApp).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        coinbaseViewModel.connect()
    }
}