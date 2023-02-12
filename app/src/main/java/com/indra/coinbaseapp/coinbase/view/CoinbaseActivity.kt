package com.indra.coinbaseapp.coinbase.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.indra.coinbaseapp.CoinbaseApp
import com.indra.coinbaseapp.coinbase.viewmodel.CoinbaseViewModel
import com.indra.coinbaseapp.databinding.ActivityMainBinding
import com.tinder.scarlet.WebSocket
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

        with(binding) {
            lifecycleOwner = this@CoinbaseActivity
        }

        setSupportActionBar(binding.toolbar)

        coinbaseViewModel.viewState.observe(this) {
            binding.viewState = it
        }

        coinbaseViewModel.viewEvents.observe(this) {
            when(it) {
                is WebSocket.Event.OnConnectionOpened<*> -> showMessage("Connection Opened")
                is WebSocket.Event.OnConnectionClosed -> showMessage("Connection Closed")
                is WebSocket.Event.OnConnectionFailed -> showMessage("Connection Failed")
                is WebSocket.Event.OnMessageReceived -> showMessage("New price update")
                else -> {
                    Log.i("WebSocket", it.toString())
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}