package com.indra.coinbaseapp

import android.app.Application
import com.indra.coinbaseapp.di.AppComponent
import com.indra.coinbaseapp.di.DaggerAppComponent

private const val SOCKET_BASE_URL = "wss://ws-feed.pro.coinbase.com"
class CoinbaseApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .appUrl(SOCKET_BASE_URL)
            .build()
    }
}