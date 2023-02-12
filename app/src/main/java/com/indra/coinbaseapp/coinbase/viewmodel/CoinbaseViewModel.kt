package com.indra.coinbaseapp.coinbase.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.indra.coinbaseapp.comm.CoinbaseService
import com.indra.coinbaseapp.comm.request.SubscribeAction
import com.indra.coinbaseapp.comm.request.TickerRequest
import com.tinder.scarlet.WebSocket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class CoinbaseViewModel @Inject constructor(
    private val coinbaseService: CoinbaseService
): ViewModel() {
    private val _viewEvents = MutableLiveData<WebSocket.Event>()
    val viewEvents: LiveData<WebSocket.Event> = _viewEvents

    private val _viewState = MutableLiveData<CoinbaseViewState>()
    val viewState: LiveData<CoinbaseViewState> = _viewState

    private val compositeDisposable = CompositeDisposable()

    init {
        coinbaseService.observeWebSocketEvent()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        subscribe()
                        _viewEvents.value = it
                    }
                    else -> {
                        _viewEvents.value = it
                    }
                }
            }, {
                Log.e("BitCoin", "connection error = ${it.cause}")
                _viewEvents.value = WebSocket.Event.OnConnectionFailed(it)
            }).also {
                it.addTo(compositeDisposable)
            }

        coinbaseService.observeTicker()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _viewState.value = CoinbaseViewState(it.price)
            }, { error ->
                _viewEvents.value = WebSocket.Event.OnConnectionFailed(error)
            }).also {
                it.addTo(compositeDisposable)
            }
    }

    private fun subscribe() {
        val productIds = listOf("ETH-BTC")
        val subscriptionRequest = listOf(TickerRequest(productIds = productIds))
        val subscriptionAction = SubscribeAction(productIds = productIds, channels = subscriptionRequest)

        coinbaseService.subscribe(subscriptionAction)
    }
}