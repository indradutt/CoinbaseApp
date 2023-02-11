package com.indra.coinbaseapp

import android.app.Application
import com.indra.coinbaseapp.comm.CoinbaseService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val SOCKET_BASE_URL = "wss://ws-feed.pro.coinbase.com"

class CoinbaseApp: Application() {

    private val backoffStrategy = ExponentialWithJitterBackoffStrategy(5000, 5000)

    lateinit var coinbaseService: CoinbaseService

    override fun onCreate() {
        super.onCreate()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        coinbaseService = Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(SOCKET_BASE_URL))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .backoffStrategy(backoffStrategy)
            .lifecycle(AndroidLifecycle.ofApplicationForeground(application = this))
            .build()
            .create()
    }
}