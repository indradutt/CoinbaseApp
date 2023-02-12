package com.indra.coinbaseapp.coinbase.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.indra.coinbaseapp.comm.CoinbaseService
import com.indra.coinbaseapp.comm.request.SubscribeAction
import com.indra.coinbaseapp.comm.request.TickerRequest
import com.tinder.scarlet.WebSocket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CoinbaseViewModel @Inject constructor(
    private val coinbaseService: CoinbaseService
): ViewModel() {
    private var disposables: CompositeDisposable = CompositeDisposable()

    private var ticker: CompositeDisposable = CompositeDisposable()

    fun connect() {
        coinbaseService.observeWebSocketEvent()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        Log.i("BitCoin", "OnConnectionOpened")
                        subscribe()
                    }
                    is WebSocket.Event.OnConnectionFailed -> {
                        Log.w("BitCoin", "connection failed")
                    }
                    is WebSocket.Event.OnConnectionClosed -> {
                        Log.d("BitCoin", "connection closed")
                    }
                    is WebSocket.Event.OnMessageReceived -> {
                        Log.i("BitCoin", "OnMessageReceived")
                    }
                    else -> {
                        Log.e("BitCoin", "some other error")
                    }
                }
            }, {
                Log.e("BitCoin", "connection error = ${it.cause}")
            }).also {
                disposables.add(it)
            }

        coinbaseService.observeTicker()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("BitCoin", "price = ${it.price}")
            }, { error ->
                Log.e("BitCoin", "error = ${error.cause}")
            }).also {
                ticker.add(it)
            }

    }

    private fun subscribe() {
        val productIds = listOf("ETH-BTC")
        val subscriptionRequest = listOf(TickerRequest(productIds = productIds))
        val subscriptionAction = SubscribeAction(productIds = productIds, channels = subscriptionRequest)

        coinbaseService.subscribe(subscriptionAction)
    }
}